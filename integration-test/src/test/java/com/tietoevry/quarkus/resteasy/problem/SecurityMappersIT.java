package com.tietoevry.quarkus.resteasy.problem;

import static com.tietoevry.quarkus.resteasy.problem.AuthTestUtils.givenAnonymous;
import static com.tietoevry.quarkus.resteasy.problem.AuthTestUtils.givenUser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.zalando.problem.Status.FORBIDDEN;
import static org.zalando.problem.Status.UNAUTHORIZED;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
class SecurityMappersIT {

    static final String SAMPLE_DETAIL = "A small one";

    static {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void unauthorizedShouldReturn401ProblemInsteadOfDefaultRestEasyDefaultResponse() {
        given()
                .queryParam("message", SAMPLE_DETAIL)
                .get("/throw/security/unauthorized-exception")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode())
                .body("title", equalTo(UNAUTHORIZED.getReasonPhrase()))
                .body("status", equalTo(UNAUTHORIZED.getStatusCode()))
                .body("detail", equalTo(SAMPLE_DETAIL))
                .body("stacktrace", nullValue());
    }

    @Test
    void authenticationFailedShouldReturn401ProblemInsteadOfDefaultRestEasyDefaultResponse() {
        given()
                .queryParam("message", SAMPLE_DETAIL)
                .get("/throw/security/authentication-failed-exception")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode())
                .body("title", equalTo(UNAUTHORIZED.getReasonPhrase()))
                .body("status", equalTo(UNAUTHORIZED.getStatusCode()))
                .body("detail", equalTo(SAMPLE_DETAIL))
                .body("stacktrace", nullValue());
    }

    @Test
    void forbiddenShouldReturn403Problem() {
        given()
                .queryParam("message", SAMPLE_DETAIL)
                .get("/throw/security/forbidden-exception")
                .then()
                .statusCode(FORBIDDEN.getStatusCode())
                .body("title", equalTo(FORBIDDEN.getReasonPhrase()))
                .body("status", equalTo(FORBIDDEN.getStatusCode()))
                .body("detail", equalTo(SAMPLE_DETAIL))
                .body("stacktrace", nullValue());
    }

    @Test
    void securedResourceWithoutValidJwtShouldReturn401() {
        givenAnonymous()
                .queryParam("message", SAMPLE_DETAIL)
                .get("/throw/security/secured-resource")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode())
                .body("title", equalTo(UNAUTHORIZED.getReasonPhrase()))
                .body("status", equalTo(UNAUTHORIZED.getStatusCode()))
                .body("stacktrace", nullValue());
    }

    @Test
    void securedResourceWithoutAllowedRoleShouldReturn403() {
        givenUser("test-user", "rfc7807-limited-access")
                .queryParam("message", SAMPLE_DETAIL)
                .get("/throw/security/secured-resource")
                .then()
                .statusCode(FORBIDDEN.getStatusCode())
                .body("title", equalTo(FORBIDDEN.getReasonPhrase()))
                .body("status", equalTo(FORBIDDEN.getStatusCode()))
                .body("stacktrace", nullValue());
    }
}
