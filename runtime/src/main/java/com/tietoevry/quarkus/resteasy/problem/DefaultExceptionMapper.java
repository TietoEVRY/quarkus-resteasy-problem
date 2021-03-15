package com.tietoevry.quarkus.resteasy.problem;

import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.ext.Provider;
import org.zalando.problem.Problem;

@Provider
@Priority(Priorities.USER)
public class DefaultExceptionMapper extends ExceptionMapperBase<Exception> {

    @Override
    protected Problem toProblem(Exception exception) {
        return Problem.valueOf(INTERNAL_SERVER_ERROR);
    }
}
