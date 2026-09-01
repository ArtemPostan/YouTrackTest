package apiTests.positive;

import based.ApiBaseTest;
import based.TestResultLoggerExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(TestResultLoggerExtension.class)
public class LoginViaApiTest extends ApiBaseTest {

    @Test
    @DisplayName("Проверка доступа с передачей поля login")
    public void testGetProfile() {
        given()
                .spec(getAuthSpec())
                .queryParam("fields", "id,login")
                .when()
                .get("/api/users/me")
                .then()
                .statusCode(200)
                .body("login", equalTo("admin"));
    }

    @Test
    @DisplayName("Отказ в доступе (401) без передачи токена")
    public void shouldDenyAccessWithoutToken() {
        given()
                .baseUri(BASE_URI)
                .header("Accept", "application/json")
                .when()
                .get("/api/users/me")
                .then()
                .statusCode(401);
    }
}
