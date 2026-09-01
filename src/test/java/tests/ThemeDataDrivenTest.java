package tests;

import based.AuthorizedBaseTest;
import based.AuthorizedGridBaseTest;
import based.GridBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HeaderPage;
import java.time.Duration;
public class ThemeDataDrivenTest extends AuthorizedGridBaseTest {

    @ParameterizedTest(name = "Проверка переключения темы на: {0}")
    @CsvSource({
            "darcula, dark",
            "default, light"
    })
    public void testThemeSwitching(String themeValue, String expectedClassPart) {

        HeaderPage headerPage = new HeaderPage(driver());

        headerPage.setTheme(themeValue);

        WebDriverWait customWait =
                new WebDriverWait(driver(), Duration.ofSeconds(5));

        boolean isThemeApplied = customWait.until(driver -> {
            String themeAttr =
                    driver.findElement(By.tagName("html"))
                            .getAttribute("class");

            return themeAttr.contains(expectedClassPart)
                    || themeAttr.contains(themeValue);
        });

        System.out.println(
                "INVOCATION: theme=" + themeValue +
                        " | expected=" + expectedClassPart +
                        " | thread=" + Thread.currentThread().getName()
        );

        Assertions.assertTrue(
                isThemeApplied,
                "Тема с параметром " + themeValue +
                        " не применилась корректно!"
        );
    }
}