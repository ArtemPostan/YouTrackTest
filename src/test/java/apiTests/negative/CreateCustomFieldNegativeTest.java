package apiTests.negative;

import based.ApiBaseTest;
import based.TestResultLoggerExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import api.CustomFieldApi;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


@ExtendWith(TestResultLoggerExtension.class)
public class CreateCustomFieldNegativeTest extends ApiBaseTest {
    private String existingFieldId;
    private final String ORIGINAL_NAME = "ZoneOfResponsibility10";

    @BeforeEach
    void setUp() {
        existingFieldId = CustomFieldApi
                .createCustomField(ORIGINAL_NAME)
                .then()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @ParameterizedTest(name = "Попытка создать поле с данными: {arguments}")
    @CsvSource({
            "ZoneOfResponsibility10, 400",
            "'', 400"
    })
    @DisplayName("Data-Driven: негативные сценарии создания кастомного поля")
    void ShouldNotAllowToCreateDuplicateField(String fieldName, int expectedStatusCode) {
        CustomFieldApi
                .createCustomField(fieldName)
                .then()
                .statusCode(expectedStatusCode);

    }

    @AfterEach
    void tearDown() {
        if (existingFieldId != null) {
            CustomFieldApi
                    .deleteCustomField(existingFieldId)
                    .then()
                    .statusCode(200);
        }
    }
}
