package tests;

import based.AuthorizedBaseTest;
import based.AuthorizedGridBaseTest;
import based.GridBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.IssuesPage;

import java.time.Duration;

public class IssueDataDrivenTest extends AuthorizedGridBaseTest {

    @ParameterizedTest(name = "Создание задачи с заголовком: {0}")
    @CsvSource({
            "Автоматический баг-репорт №1",
            "Тестовая задача для проверки функционала №2"
    })
    public void testCreateMultipleIssues(String issueSummary) {

        System.out.println(
                "INVOCATION: " + issueSummary +
                        " | THREAD: " + Thread.currentThread().getName()
        );

        IssuesPage issuesPage = new IssuesPage(driver());

        issuesPage.createNewIssue(issueSummary);

        WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(10));

        boolean isUrlCorrect = wait.until(
                driver -> driver.getCurrentUrl().contains("/issue/")
        );

        Assertions.assertTrue(
                isUrlCorrect,
                "URL не содержит '/issue/', задача, возможно, не создалась!"
        );
    }
}