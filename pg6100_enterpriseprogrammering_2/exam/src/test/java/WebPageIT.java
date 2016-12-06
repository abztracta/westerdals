import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Espen Rønning
 * @version 2016-05-27
 * @since 1.8
 */
public class WebPageIT {

    private static final String HOME_URL = "http://localhost:8080/pg6100_exam";

    private WebDriver driver;
    private HomePageObject homePageObject;
    private SignInPageObject signInPageObject;
    private CreateUserPageObject createUserPageObject;
    private CreateEventPageObject createEventPageObject;

    private static WireMockServer mockServer;

    @BeforeClass
    public static void init() {
        mockServer = new WireMockServer(new WireMockConfiguration().port(8989).notifier(new ConsoleNotifier(true)));
        mockServer.start();
    }

    @AfterClass
    public static void destroy() {
        mockServer.stop();
    }

    private void mockRequestToRestcountriesApi() {
        String jsonResponse = getResourceFileData("countries.json");
        mockServer.stubFor(get(urlEqualTo("/rest/v1/all"))
                .willReturn(aResponse()
                    .withBody(jsonResponse)));
    }

    private void verifyRequestToRestcountriesApi() {
        mockServer.verify(getRequestedFor(urlEqualTo("/rest/v1/all")));
    }

    // inspiration taken from http://www.mkyong.com/java/java-read-a-file-from-resources-folder/
    private String getResourceFileData(String filename) {
        String data = "";
        ClassLoader classLoader = getClass().getClassLoader();
        try (Scanner scanner = new Scanner(classLoader.getResourceAsStream(filename))) {
            while (scanner.hasNext()) {
                data += scanner.nextLine();
            }
        }
        return data;
    }

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("Firefox");
        desiredCapabilities.setJavascriptEnabled(true);
        driver = new FirefoxDriver(desiredCapabilities);
        homePageObject = new HomePageObject(driver);
        signInPageObject = new SignInPageObject(driver);
        createUserPageObject = new CreateUserPageObject(driver);
        createEventPageObject = new CreateEventPageObject(driver);
        mockRequestToRestcountriesApi();
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }

    @Test
    public void testHomePage() throws Exception {
        driver.get(HOME_URL);
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
    }

    @Test
    public void testLoginLink() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        assertEquals("PG6100 Exam :: Sign In", driver.getTitle());
    }

    @Test
    public void testLoginWrongUser() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        assertEquals("PG6100 Exam :: Sign In", driver.getTitle());
        signInPageObject.enterLoginForm("Johnny", "McNoPassword");
        assertEquals("PG6100 Exam :: Sign In", driver.getTitle());
    }

    @Test
    public void testCreateUserFailDueToPasswordMismatch() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        assertEquals("PG6100 Exam :: Sign In", driver.getTitle());
        signInPageObject.clickRegistrationLink();
        assertEquals("PG6100 Exam :: Create New User", driver.getTitle());
        createUserPageObject.fillInRegistrationForm("ronesp13", "Password", "PasswordConfirmation", "Espen", null, "Roenning", "Norway");
        assertEquals("PG6100 Exam :: Create New User", driver.getTitle());
    }

    @Test
    public void testCreateValidUser() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        assertEquals("PG6100 Exam :: Create New User", driver.getTitle());
        createUserPageObject.fillInRegistrationForm("strpia13", "Password", "Password", "Pia", "Dokken", "Stranger-Johannessen", "Norway");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        assertTrue(homePageObject.isCurrentUser("Pia Stranger-Johannessen"));
        assertTrue(homePageObject.isSignOutButtonPresent());
        assertFalse(homePageObject.isSignInButtonPresent());
        homePageObject.clickSignOutButton();
    }

    @Test
    public void testLogin() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("ronesp13", "Password", "Password", "Espen", null, "Roenning", "Norway");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickSignOutButton();
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickSignInButton();
        signInPageObject.enterLoginForm("ronesp13", "Password");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        assertTrue(homePageObject.isCurrentUser("Espen Roenning"));
        assertTrue(homePageObject.isSignOutButtonPresent());
        assertFalse(homePageObject.isSignInButtonPresent());
        homePageObject.clickSignOutButton();
    }

    @Test
    public void testCreateOneEvent() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("mikand13", "Password", "Password", "Anders", null, "Mikkelsen", "Norway");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        int numberOfEventsBeforeCreateEvent = homePageObject.getNumberOfEvents();
        homePageObject.clickCreateEventButton();
        assertEquals("PG6100 Exam :: Create New Event", driver.getTitle());
        createEventPageObject.fillInForm("Pool party", "Norway", "The beach", "Party starts at 1800 hours!");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        assertEquals(numberOfEventsBeforeCreateEvent + 1, homePageObject.getNumberOfEvents());
        homePageObject.clickSignOutButton();
    }

    @Test
    public void testCreateEventInDifferentCountries() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("raaeli13", "Password", "Password", "Eline", "Moen", "Raanaas", "Norway");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickShowElementsByCountryButton();
        int numberOfEventsBeforeCreateEvent = homePageObject.getNumberOfEvents();
        int numberOfEventsBeforeCreateEventByCountry = homePageObject.getEventsInCountry("Norway");
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInForm("Nice dog meeting", "Norway", "Some place", "Bring your best doge.");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        assertEquals(numberOfEventsBeforeCreateEvent + 1, homePageObject.getNumberOfEvents());
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInForm("Nice cat meeting", "Sweden", "Another place", "Bring a nice cat.");
        assertEquals(numberOfEventsBeforeCreateEvent + 2, homePageObject.getNumberOfEvents());
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickShowElementsByCountryButton();
        assertEquals(numberOfEventsBeforeCreateEventByCountry + 1, homePageObject.getNumberOfEvents());
    }

    @Test
    public void testCreateEventsFromDifferentUsers() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("arcand", "Password", "Password", "Andrea", null, "Arcuri", "Italy");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickShowElementsByCountryButton();
        int numberOfEventsBeforeCreateEventForFirstUser = homePageObject.getNumberOfEvents();
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInForm("Selenium", "Italy", "Rome", "I will teach Selenium.");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        assertEquals(numberOfEventsBeforeCreateEventForFirstUser + 1, homePageObject.getNumberOfEvents());
        homePageObject.clickSignOutButton();
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("lauper", "Password", "Password", "Per", null, "Lauvås", "Norway");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickShowElementsByCountryButton();
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInForm("Servlets & JSP", "Norway", "Westerdals", "Servlets & JSP 4tw.");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        assertEquals(numberOfEventsBeforeCreateEventForFirstUser + 2, homePageObject.getNumberOfEvents());
        assertTrue(homePageObject.isEventPresent("Selenium", "Italy", "Rome"));
        assertTrue(homePageObject.isEventPresent("Servlets & JSP", "Norway", "Westerdals"));
    }

    @Test
    public void testCreateUserWithFakeLand() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("breeiv", "Password", "Password", "Eivind", null, "Brevik", "Norway");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInFormWithoutPresentCountryInList("wrong");
        assertEquals("PG6100 Exam :: Create New Event", driver.getTitle());
        createEventPageObject.fillInForm("How to be a awesome principal", "Fake Land", "Westerdals", "Title says it all.");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
    }

    @Test
    public void testCreateAndAttendEvent() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("karfal", "Password", "Password", "Faltin", null, "Karlsen", "Norway");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInForm("Research", "Norway", "Westerdals", "Research");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        assertTrue(homePageObject.isParticipantCount("Research", 0));
        homePageObject.clickAttendanceButton("Research");
        assertTrue(homePageObject.isParticipantCount("Research", 1));
        homePageObject.clickAttendanceButton("Research");
        assertTrue(homePageObject.isParticipantCount("Research", 0));
    }

    @Test
    public void testTwoUsersAttendingSameEvent() throws Exception {
        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("raakje", "Password", "Password", "Kjetil", null, "Raaen", "Norway");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInForm("Responsive Gaming", "Norway", "Westerdals", "Fast stuff");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickAttendanceButton("Responsive Gaming");
        assertTrue(homePageObject.isParticipantCount("Responsive Gaming", 1));
        homePageObject.clickSignOutButton();
        assertEquals("PG6100 Exam :: Home", driver.getTitle());

        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("listog", "Password", "Password", "Bjoern Olav", null, "Listog", "Norway");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        homePageObject.clickAttendanceButton("Responsive Gaming");
        assertTrue(homePageObject.isParticipantCount("Responsive Gaming", 2));
        homePageObject.clickSignOutButton();
        assertEquals("PG6100 Exam :: Home", driver.getTitle());

        homePageObject.clickSignInButton();
        signInPageObject.enterLoginForm("raakje", "Password");
        assertEquals("PG6100 Exam :: Home", driver.getTitle());
        assertTrue(homePageObject.isParticipantCount("Responsive Gaming", 2));
        homePageObject.clickAttendanceButton("Responsive Gaming");
        assertTrue(homePageObject.isParticipantCount("Responsive Gaming", 1));
    }
}
