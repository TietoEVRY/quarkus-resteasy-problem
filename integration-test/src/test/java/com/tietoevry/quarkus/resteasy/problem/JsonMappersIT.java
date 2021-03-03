package com.tietoevry.quarkus.resteasy.problem;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.zalando.problem.Status.BAD_REQUEST;

@QuarkusTest
class JsonMappersIT {

    static {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Should return Bad Request(400) when request payload is malformed #1")
    void shouldThrowBadRequestOnMalformedBody() {
        given()
                .body("{\"key\":\"")
                .contentType(APPLICATION_JSON)
                .post("/throw/json")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode());
    }

    @Test
    @DisplayName("Should return Bad Request(400) when request payload is malformed #2")
    void shouldThrowBadRequestOnDifferentlyMalformedBody() {
        given()
                .body("{\"key\":")
                .contentType(APPLICATION_JSON)
                .post("/throw/json")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode());
    }
}