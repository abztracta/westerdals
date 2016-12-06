import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import no.woact.model.Event;
import no.woact.model.User;
import no.woact.service.dto.EventListDto;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
public class RestIT {

    private static final String HOME_URL = "http://localhost:8080/pg6100_exam";
    private static WireMockServer mockServer;

    private static Long firstEventId;

    @BeforeClass
    public static void init() {
        mockServer = new WireMockServer(new WireMockConfiguration().port(8989).notifier(new ConsoleNotifier(true)));
        mockServer.start();
        mockRequestToRestcountriesApi();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        capabilities.setJavascriptEnabled(true);
        WebDriver driver = new FirefoxDriver(capabilities);
        HomePageObject homePageObject = new HomePageObject(driver);
        SignInPageObject signInPageObject = new SignInPageObject(driver);
        CreateUserPageObject createUserPageObject = new CreateUserPageObject(driver);
        CreateEventPageObject createEventPageObject = new CreateEventPageObject(driver);

        driver.get(HOME_URL);
        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("grotor", "Password", "Password", "Tor-Morten", null, "Groenli", "Norway");
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInForm("Learn Android", "Norway", "Westerdals", "JellyBean");
        homePageObject.clickAttendanceButton("Learn Android");
        homePageObject.clickSignOutButton();

        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("sydlar", "Password", "Password", "Lars", null, "Sydnes", "Norway");
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInForm("Maths & Physics", "Norway", "Westerdals", "Why stuff works.");
        homePageObject.clickAttendanceButton("Learn Android");
        homePageObject.clickAttendanceButton("Maths & Physics");
        homePageObject.clickSignOutButton();

        homePageObject.clickSignInButton();
        signInPageObject.clickRegistrationLink();
        createUserPageObject.fillInRegistrationForm("batman", "Password", "Password", "Bruce", null, "Wayne", "United States");
        homePageObject.clickCreateEventButton();
        createEventPageObject.fillInForm("How to be a hero", "United States", "Gothham", "Learn to do Bat-stuff");
        homePageObject.clickShowElementsByCountryButton();
        homePageObject.clickAttendanceButton("Learn Android");
        homePageObject.clickAttendanceButton("Maths & Physics");
        homePageObject.clickAttendanceButton("How to be a hero");
        homePageObject.clickSignOutButton();

        driver.close();
        verifyRequestToRestcountriesApi();


        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").path("all");
        Response response = target.request().accept(MediaType.APPLICATION_JSON).get();
        EventListDto eventListDto = response.readEntity(EventListDto.class);
        List<Event> filtered = eventListDto.getEvents()
                .stream()
                .filter(e -> e.getTitle().equals("Learn Android"))
                .collect(Collectors.toList());
        firstEventId = filtered.get(0).getId();
        response.close();
    }

    @AfterClass
    public static void destroy() {
        mockServer.stop();
    }

    @Before
    public void setUp() {
        mockRequestToRestcountriesApi();
    }

    private static void mockRequestToRestcountriesApi() {
        String jsonResponse = getResourceFileData("countries.json");
        mockServer.stubFor(get(urlEqualTo("/rest/v1/all"))
                .willReturn(aResponse()
                        .withBody(jsonResponse)));
    }

    private static void verifyRequestToRestcountriesApi() {
        mockServer.verify(getRequestedFor(urlEqualTo("/rest/v1/all")));
    }

    // inspiration taken from http://www.mkyong.com/java/java-read-a-file-from-resources-folder/
    private static String getResourceFileData(String filename) {
        String data = "";
        ClassLoader classLoader = RestIT.class.getClassLoader();
        try (Scanner scanner = new Scanner(classLoader.getResourceAsStream(filename))) {
            while (scanner.hasNext()) {
                data += scanner.nextLine();
            }
        }
        return data;
    }

    @Test
    public void testGetAllXml() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").path("all");
        Response response = target.request().accept(MediaType.APPLICATION_XML).get();
        EventListDto eventListDto = response.readEntity(EventListDto.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        response.close();

        assertTrue(eventListDto.getEvents().size() >= 3);
        List<Event> events = eventListDto.getEvents()
                .stream()
                .filter(event -> (event.getTitle().equals("How to be a hero") || event.getTitle().equals("Maths & Physics") ||  event.getTitle().equals("Learn Android")))
                .collect(Collectors.toList());
        assertEquals(3, events.size());
    }

    @Test
    public void testGetCountryAXml() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").path("all").queryParam("country", "Norway");
        Response response = target.request().accept(MediaType.APPLICATION_XML).get();
        EventListDto eventListDto = response.readEntity(EventListDto.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        response.close();

        assertTrue(eventListDto.getEvents().size() >= 2);
        List<Event> present = eventListDto.getEvents()
                .stream()
                .filter(event -> (event.getTitle().equals("Maths & Physics") || event.getTitle().equals("Learn Android")))
                .collect(Collectors.toList());
        assertEquals(2, present.size());

        List<Event> emptyList = eventListDto.getEvents()
                .stream()
                .filter(event -> (event.getTitle().equals("How to be a hero")))
                .collect(Collectors.toList());
        assertEquals(0, emptyList.size());
    }

    @Test
    public void testGetCountryBXml() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").path("all").queryParam("country", "United States");
        Response response = target.request().accept(MediaType.APPLICATION_XML).get();
        EventListDto eventListDto = response.readEntity(EventListDto.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        response.close();

        assertTrue(eventListDto.getEvents().size() >= 1);
        List<Event> present = eventListDto.getEvents()
                .stream()
                .filter(event -> (event.getTitle().equals("How to be a hero")))
                .collect(Collectors.toList());
        assertEquals(1, present.size());

        List<Event> emptyList = eventListDto.getEvents()
                .stream()
                .filter(event -> (event.getTitle().equals("Learn Android") || event.getTitle().equals("Maths & Physics")))
                .collect(Collectors.toList());
        assertEquals(0, emptyList.size());
    }

    @Test
    public void testGetAllJson() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").path("all");
        Response response = target.request().accept(MediaType.APPLICATION_JSON).get();
        EventListDto eventListDto = response.readEntity(EventListDto.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        response.close();

        assertTrue(eventListDto.getEvents().size() >= 3);
        List<Event> events = eventListDto.getEvents()
                .stream()
                .filter(event -> (event.getTitle().equals("How to be a hero") || event.getTitle().equals("Maths & Physics") ||  event.getTitle().equals("Learn Android")))
                .collect(Collectors.toList());
        assertEquals(3, events.size());
    }

    @Test
    public void testGetEventXml() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").path("all");
        Response response = target.request().accept(MediaType.APPLICATION_XML).get();
        EventListDto eventListDto = response.readEntity(EventListDto.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        response.close();

        Event event = eventListDto.getEvents().get(0);
        target = client.target(HOME_URL).path("rs").path("events").queryParam("id", event.getId());
        response = target.request(MediaType.APPLICATION_XML).get();
        Event singleEvent = response.readEntity(Event.class);

        assertEquals(event.getId(), singleEvent.getId());
        assertEquals(event.getTitle(), singleEvent.getTitle());
        assertEquals(event.getCountry(), singleEvent.getCountry());
        assertEquals(event.getLocation(), singleEvent.getLocation());
        assertEquals(event.getDescription(), singleEvent.getDescription());
        response.close();
    }

    @Test
    public void testGetEventJson() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").path("all");
        Response response = target.request().accept(MediaType.APPLICATION_JSON).get();
        EventListDto eventListDto = response.readEntity(EventListDto.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        response.close();

        Event event = eventListDto.getEvents().get(0);
        target = client.target(HOME_URL).path("rs").path("events").queryParam("id", event.getId());
        response = target.request(MediaType.APPLICATION_JSON).get();
        Event singleEvent = response.readEntity(Event.class);

        assertEquals(event.getId(), singleEvent.getId());
        assertEquals(event.getTitle(), singleEvent.getTitle());
        assertEquals(event.getCountry(), singleEvent.getCountry());
        assertEquals(event.getLocation(), singleEvent.getLocation());
        assertEquals(event.getDescription(), singleEvent.getDescription());
        response.close();
    }

    @Test
    public void testWrongId() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").queryParam("id", Long.MAX_VALUE);
        Response response = target.request().get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        response.close();
    }

    @Test
    public void testGetAllWithAttendeesXml() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").path("all").queryParam("attendees", true);
        Response response = target.request().accept(MediaType.APPLICATION_XML).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        EventListDto eventListDto = response.readEntity(EventListDto.class);
        eventListDto.getEvents()
                .stream()
                .forEach(e -> {
                    if (e.getTitle().equals("Learn Android")) {
                        assertEquals(3, e.getParticipants().size());
                    }
                    if (e.getTitle().equals("Maths & Physics")) {
                        assertEquals(2, e.getParticipants().size());
                    }
                    if (e.getTitle().equals("How to be a hero")) {
                        assertEquals(1, e.getParticipants().size());
                    }
                });
        response.close();
    }

    @Test
    public void testGetEventWithAttendeesXml() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(HOME_URL).path("rs").path("events").queryParam("attendees", true).queryParam("id", firstEventId);
        Response response = target.request().accept(MediaType.APPLICATION_XML).get();
        String data = response.readEntity(String.class);
        String expectedResult = getResourceFileData("first_event.xml");
        assertEquals(expectedResult, data);
        response.close();

        Event event = JAXB.unmarshal(new StringReader(data), Event.class);
        List<User> participants = event.getParticipants();
        assertEquals(3, participants.size());
    }
}
