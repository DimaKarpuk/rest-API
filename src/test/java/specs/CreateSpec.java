package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class CreateSpec {
    public static RequestSpecification requestSpecification = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().all();
    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .log(ALL)
            .build();
}