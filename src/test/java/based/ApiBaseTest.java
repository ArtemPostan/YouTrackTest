package based;

import api.CustomFieldApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ApiBaseTest {
    protected static final String BASE_URI = System.getProperty("base.uri", "http://localhost:8080");

    private static final String VALID_TOKEN = "perm-YWRtaW4=.NDUtMA==.61T2xllDR1Gag2qQD0Fb7QTdIuiqMO";

    public static RequestSpecification getAuthSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeader("Authorization", "Bearer " + VALID_TOKEN)
                .addHeader("Accept", "application/json")
                .setContentType("application/json")
                .build();
    }

}