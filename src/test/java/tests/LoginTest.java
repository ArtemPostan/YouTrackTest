package tests;

import based.BaseTest;
import based.GridBaseTest;
import pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginTest extends GridBaseTest {

    @Test
    public void testSuccessfulLogin() {

        driver().get(BASE_URL);

        LoginPage loginPage = new LoginPage(driver());
        loginPage.login("admin", "123");

        String currentUrl = driver().getCurrentUrl();
        Assertions.assertFalse(currentUrl.contains("login"), "Login failed, still on the login page!");
    }
}