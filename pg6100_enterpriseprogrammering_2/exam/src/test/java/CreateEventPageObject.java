import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
public class CreateEventPageObject {

    private WebDriver driver;

    public CreateEventPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void fillInForm(String title, String country, String location, String description) {
        driver.findElement(By.id("create_event_form:title")).sendKeys(title);
        new Select(driver.findElement(By.id("create_event_form:country"))).selectByVisibleText(country);
        driver.findElement(By.id("create_event_form:location")).sendKeys(location);
        driver.findElement(By.id("create_event_form:description")).sendKeys(description);
        driver.findElement(By.id("create_event_form:submit")).click();
    }

    public boolean fillInFormWithoutPresentCountryInList(String country) {
        try {
            new Select(driver.findElement(By.id("create_event_form:country"))).selectByVisibleText(country);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
