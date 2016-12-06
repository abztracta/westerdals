import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Espen Rønning
 * @version 2016-05-27
 * @since 1.8
 */
public class HomePageObject {

    private WebDriver driver;

    public HomePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSignInButton() {
        driver.findElement(By.id("main_sign_in_button")).click();
    }

    public void clickSignOutButton() {
        driver.findElement(By.id("sign_out_form:button")).click();
    }

    public boolean isSignInButtonPresent() {
        return driver.findElements(By.id("main_sign_in_button")).size() > 0;
    }

    public boolean isSignOutButtonPresent() {
        return driver.findElements(By.id("sign_out_form:button")).size() > 0;
    }

    public boolean isCurrentUser(String name) {
        return driver.findElement(By.id("current_user_div")).getText().contains(name);
    }

    public void clickCreateEventButton() {
        driver.findElement(By.id("create_event_button")).click();
    }

    public int getNumberOfEvents() {
        return driver.findElements(By.cssSelector(("#event_table tbody tr"))).size();
    }

    public int getEventsInCountry(String country) {
        return driver.findElements(By.cssSelector("#event_table tbody tr td:nth-child(2)"))
                .stream()
                .filter(event -> event.getText().equals(country))
                .map(event -> event)
                .collect(Collectors.toList())
                .size();
    }

    public void clickShowElementsByCountryButton() {
        driver.findElement(By.id("country_change_form:checkbox")).click();
    }

    public boolean isEventPresent(String title, String country, String location) {
        return driver.findElements(By.cssSelector("#event_table tbody tr"))
                .stream()
                .filter(event -> isEventPresent(event, title, country, location))
                .map(event -> event)
                .collect(Collectors.toList())
                .size() != 0;
    }

    private boolean isEventPresent(WebElement element, String title, String country, String location) {
        return element.getText().contains(title) && element.getText().contains(country) && element.getText().contains(location);
    }

    public void clickAttendanceButton(String event) {
        List<WebElement> elements = driver.findElements(By.cssSelector("#event_table tbody tr"));
        List<WebElement> parentElementList = elements.stream()
                .filter(e -> e.getText().contains(event))
                .collect(Collectors.toList());
        WebElement element = parentElementList.get(0);
        WebElement attendanceButton = element.findElement(By.cssSelector("td:nth-child(5) input[type=checkbox]"));
        attendanceButton.click();
    }

    public boolean isParticipantCount(String event, int count) {
        Document document = Jsoup.parse(driver.getPageSource());
        Elements select = document.select("#event_table tbody tr");
        List<Element> rows = select.stream().filter(e -> e.text().contains(event)).collect(Collectors.toList());
        Element element = rows.get(0);
        Elements children = element.children();
        Element child = children.get(3);
        return Integer.parseInt(child.text().trim()) == count;
    }
}
