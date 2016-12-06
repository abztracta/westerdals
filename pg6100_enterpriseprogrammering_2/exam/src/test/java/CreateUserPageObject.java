import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
public class CreateUserPageObject {

    private WebDriver driver;

    public CreateUserPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void fillInRegistrationForm(String username, String password, String passwordConfirmation, String firstName, String middleName, String lastName, String country) {
        driver.findElement(By.id("reg_form:username")).sendKeys(username);
        driver.findElement(By.id("reg_form:password")).sendKeys(password);
        driver.findElement(By.id("reg_form:password_confirmation")).sendKeys(passwordConfirmation);
        driver.findElement(By.id("reg_form:first_name")).sendKeys(firstName);
        driver.findElement(By.id("reg_form:middle_name")).sendKeys(middleName);
        driver.findElement(By.id("reg_form:last_name")).sendKeys(lastName);
        new Select(driver.findElement(By.id("reg_form:country"))).selectByVisibleText(country);
        driver.findElement(By.id("reg_form:submit")).click();
    }
}
