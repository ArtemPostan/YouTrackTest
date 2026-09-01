package based;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.net.MalformedURLException;
import java.net.URL;

public class GridBaseTest {
    protected static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    protected final String BASE_URL = "http://youtrack-server:8080";


    @BeforeEach
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();

        // Указываем адрес нашего Selenium Grid Hub в Docker
        URL gridUrl = new URL("http://localhost:4444/");

        // Создаем удаленный драйвер
        WebDriver driver = new RemoteWebDriver(gridUrl, options);
        driverThread.set(driver);
        driver.manage().window().maximize();

        // Просто открываем базовый URL, а авторизацию каждый тест будет делать сам там, где это нужно
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {

        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }

    protected WebDriver driver() {
        return driverThread.get();
    }
}