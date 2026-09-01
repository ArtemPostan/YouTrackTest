package based;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        // Проверяем, упал ли тест
        boolean isFailed = context.getExecutionException().isPresent();

        if (isFailed) {
            // Получаем экземпляр текущего тестового класса, чтобы достать из него драйвер
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof BaseTest) {
                WebDriver driver = ((BaseTest) testInstance).driver;
                if (driver != null) {
                    takeScreenshot(driver, context.getTestMethod().get().getName());
                }
            }
        }
    }

    private void takeScreenshot(WebDriver driver, String testName) {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String dirPath = "target/screenshots";
        String filePath = dirPath + "/" + testName + "_" + timestamp + ".png";

        try {
            Files.createDirectories(Paths.get(dirPath));
            FileUtils.copyFile(screenshotFile, new File(filePath));
            System.out.println("⚠️ Тест упал! Скриншот сохранен: " + filePath);
        } catch (IOException e) {
            System.err.println("Не удалось сохранить скриншот: " + e.getMessage());
        }
    }
}