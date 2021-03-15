package com.tietoevry.quarkus.resteasy.problem.jaxrs;

import com.tietoevry.quarkus.resteasy.problem.ExceptionMapperBase;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Provider
@Priority(Priorities.USER)
public class WebApplicationExceptionMapper extends ExceptionMapperBase<WebApplicationException> {

    private static final Logger logger = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);

    @Override
    protected Problem toProblem(WebApplicationException exception) {
        Status status = Status.INTERNAL_SERVER_ERROR;
        try {
            status = Status.valueOf(exception.getResponse().getStatus());
        } catch (IllegalArgumentException e) {
            logger.error("WebApplicationException status code is not a valid HTTP status: {}",
                    exception.getResponse().getStatus());
        }
        return Problem.valueOf(status, exception.getMessage());
    }

}
