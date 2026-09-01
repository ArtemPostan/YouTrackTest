package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IssuesPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локатор кнопки создания задачи в шапке YouTrack (обычно плюс или кнопка Create)
    private By createButtonInHeader = By.xpath("//span[text()='Создать']/ancestor::button");

    private By newIssueMenuItem = By.cssSelector("[href*='newIssue']");
    // Локатор поля ввода summary (заголовка задачи) в модалке создания
    private By summaryInput = By.cssSelector("textarea[data-test='summary']");

    // Локатор кнопки подтверждения создания задачи
    private By submitButton = By.cssSelector("button[data-test='submit-button']");

    public IssuesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void createNewIssue(String summaryText) {
        String originalWindow = driver.getWindowHandle();

        // 1. Открываем меню
        WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(createButtonInHeader));
        createBtn.click();

        // 2. Кликаем по "Новая задача"
        WebElement newIssueOption = wait.until(ExpectedConditions.elementToBeClickable(newIssueMenuItem));
        newIssueOption.click();

        // 3. Переключаемся на новую вкладку
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // 4. Работаем в новой вкладке
        WebElement summaryField = wait.until(ExpectedConditions.visibilityOfElementLocated(summaryInput));
        summaryField.sendKeys(summaryText);

        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submit.click();
    }

}