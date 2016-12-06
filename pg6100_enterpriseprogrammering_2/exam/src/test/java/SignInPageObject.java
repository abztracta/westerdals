import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
public class SignInPageObject {

    private WebDriver driver;

    public SignInPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void clickRegistrationLink() {
        driver.findElement(By.id("registration_form_button")).click();
    }

    public void enterLoginForm(String username, String password) {
        driver.findElement(By.id("sign_in_form:username")).sendKeys(username);
        driver.findElement(By.id("sign_in_form:password")).sendKeys(password);
        driver.findElement(By.id("sign_in_form:submit")).click();
    }
}
