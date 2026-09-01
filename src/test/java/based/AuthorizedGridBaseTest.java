package based;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.LoginPage;

import java.net.MalformedURLException;
import java.net.URL;

public class AuthorizedGridBaseTest extends GridBaseTest {

    @Override
    @BeforeEach
    public void setUp() throws MalformedURLException {
        // 1. Вызываем setUp() родительского класса (он запустит браузер, развернет окно и откроет BASE_URL)
        super.setUp();

        // 2. Сразу выполняем вход для тестов, которым нужна авторизация
        LoginPage loginPage = new LoginPage(driver());
        loginPage.login("admin", "123");
    }

    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
