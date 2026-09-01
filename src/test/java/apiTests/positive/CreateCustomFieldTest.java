package apiTests.positive;

import api.CustomFieldApi;
import based.ApiBaseTest;
import based.TestResultLoggerExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(TestResultLoggerExtension.class)
public class CreateCustomFieldTest extends ApiBaseTest {

    private String createdCustomFieldId;

    @Test
    void createCustomField() {
        createdCustomFieldId =
                CustomFieldApi
                        .createCustomField("ZoneOfResponsibility10")
                        .then()
                        .statusCode(200)
                        .body("name", equalTo("ZoneOfResponsibility10"))
                        .extract()
                        .path("id");
    }

    @AfterEach
    void tearDown() {

        if (createdCustomFieldId != null) {
            CustomFieldApi
                    .deleteCustomField(createdCustomFieldId)
                    .then()
                    .statusCode(200);
        }
    }
}
