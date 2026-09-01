package based;

import org.junit.jupiter.api.BeforeEach;
import pages.LoginPage;

public class AuthorizedBaseTest extends BaseTest {

    @BeforeEach
    public void loginBeforeEach() {
        driver.get(BASE_URL);
        LoginPage loginPage = new LoginPage(driver);
        // Используем ваши актуальные учетные данные администратора
        loginPage.login("admin", "123");
    }
}
