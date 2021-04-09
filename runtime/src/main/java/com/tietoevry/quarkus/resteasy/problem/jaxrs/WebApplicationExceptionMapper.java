package com.tietoevry.quarkus.resteasy.problem.jaxrs;

import java.util.Optional;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.tietoevry.quarkus.resteasy.problem.ExceptionMapperBase;
import com.tietoevry.quarkus.resteasy.problem.HttpProblem;

/**
 * Generic exception mapper for JaxRS WebApplicationExceptions - it passes status and message to application/problem response.
 */
@Priority(Priorities.USER)
public final class WebApplicationExceptionMapper extends ExceptionMapperBase<WebApplicationException> {

    @Override
    protected HttpProblem toProblem(WebApplicationException exception) {
        Response.StatusType status = Optional.ofNullable(exception.getResponse().getStatusInfo())
                .orElse(Response.Status.INTERNAL_SERVER_ERROR);

        HttpProblem.Builder problem = HttpProblem.builder()
                .withTitle(status.getReasonPhrase())
                .withStatus(status)
                .withDetail(exception.getMessage());

        exception
                .getResponse()
                .getHeaders()
                .forEach((header, values) -> values.forEach(value -> problem.withHeader(header, value)));

        return problem.build();
    }

}
