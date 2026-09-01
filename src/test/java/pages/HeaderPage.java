package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HeaderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By userAvatar = By.cssSelector("[data-test='avatar']");
    private By appearanceMenuLink = By.xpath("//span[contains(@class, 'header__profile-link-like-item') and text()='Оформление']");
    private By issuesButton = By.cssSelector("[data-test~='issues-button']");
    private By issue = By.cssSelector("[data-test~='ticket-id']");
    private By issueCommentField = By.cssSelector("[data-test~='wysiwyg-editor-content']");
    private By submitButton = By.cssSelector("[data-test~='post-comment']");
    private By confirmButton = By.cssSelector("[data-test~='confirm-ok-button']");

    public HeaderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Вспомогательный метод для клика через JavaScript (обходит проблему скрытых input-ов в кастомных UI)
    private void clickElementViaJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void setTheme(String themeValue) {
        // 1. Открываем аватар
        WebElement avatar = wait.until(ExpectedConditions.elementToBeClickable(userAvatar));
        avatar.click();

        // 2. Открываем оформление
        WebElement appearanceLink = wait.until(ExpectedConditions.elementToBeClickable(appearanceMenuLink));
        appearanceLink.click();

        // 3. Выбираем тему по переданному параметру (value)
        By themeRadio = By.cssSelector("input[value='" + themeValue + "']");
        WebElement themeInput = wait.until(ExpectedConditions.presenceOfElementLocated(themeRadio));

        clickElementViaJs(themeInput);

        // Небольшая пауза на применение темы
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public void createComment(String message) {

        // Открываем список задач
        WebElement issues = wait.until(
                ExpectedConditions.elementToBeClickable(issuesButton)
        );
        issues.click();

        // Ждем появления конкретной задачи
        WebElement ticket = wait.until(
                ExpectedConditions.visibilityOfElementLocated(this.issue)
        );

        // Ждем, пока она станет кликабельной
        wait.until(
                ExpectedConditions.elementToBeClickable(ticket)
        ).click();

        // Поле комментария
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(issueCommentField)
        );

        input.click();
        input.sendKeys(message);

        // Отправить
        WebElement submit = wait.until(
                ExpectedConditions.elementToBeClickable(this.submitButton)
        );

        submit.click();

        // Ждем появления комментария
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-test='comment-content']")
                )
        );
    }

    public String getCommentText(String text) {

        By commentLocator = By.xpath(
                "//*[@data-test='comment-content' and normalize-space()='" + text + "']"
        );

        WebElement comment = wait.until(
                ExpectedConditions.visibilityOfElementLocated(commentLocator)
        );

        return comment.getText();
    }

    public void deleteComment(String text) {

        // Ищем комментарий с конкретным текстом
        By commentLocator = By.xpath(
                "//*[@data-test='change-item']" +
                        "//*[@data-test='comment-content' and normalize-space()='" + text + "']"
        );

        // Находим сам комментарий
        WebElement comment = wait.until(
                ExpectedConditions.visibilityOfElementLocated(commentLocator)
        );

        // Поднимаемся от comment-content к его change-item
        WebElement changeItem = comment.findElement(
                By.xpath("./ancestor::*[@data-test='change-item']")
        );

        // Наводим мышь на конкретный change-item,
        // чтобы появилось меню комментария
        Actions actions = new Actions(driver);
        actions.moveToElement(changeItem).perform();

        // Находим меню именно внутри найденного change-item
        WebElement commentMenu = changeItem.findElement(
                By.cssSelector("[data-test='comment-menu']")
        );

        wait.until(ExpectedConditions.elementToBeClickable(commentMenu))
                .click();

        // Нажимаем "Удалить"
        WebElement deleteButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("span[title='Удалить']")
                )
        );

        deleteButton.click();

        // Подтверждаем удаление
        WebElement confirmButtonElement = wait.until(
                ExpectedConditions.elementToBeClickable(confirmButton)
        );

        confirmButtonElement.click();
    }
}