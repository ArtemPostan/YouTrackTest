package api;

import based.ApiBaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

import static based.ApiBaseTest.getAuthSpec;

public class CustomFieldApi {

    public static Response createCustomField(String name) {

    String requestBody = """
                {
                  "fieldType": {
                    "id": "enum[1]"
                  },
                  "name": "%s",
                  "isDisplayedInIssueList": true,
                  "isAutoAttached": false
                }
                """.formatted(name);

        return RestAssured
                .given()
                .spec(ApiBaseTest.getAuthSpec())
                .body(requestBody)
                .queryParam(
                        "fields",
                        "id,name,fieldType(presentation,id),isAutoAttached,isDisplayedInIssueList"
                )
                .post("/api/admin/customFieldSettings/customFields");
    }

    public static Response deleteCustomField(String id) {

        return RestAssured
                .given()
                .spec(ApiBaseTest.getAuthSpec())
                .delete("/api/admin/customFieldSettings/customFields/{id}", id);
    }
}
