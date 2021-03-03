package com.tietoevry.quarkus.resteasy.problem;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/throw/generic/")
@Produces(MediaType.APPLICATION_JSON)
public class GenericExceptionsResource {

    @GET
    @Path("/runtime-exception")
    public void throwRuntimeException(@QueryParam("message") String message) {
        throw new TestRuntimeException(message);
    }

    @GET
    @Path("/problem")
    public void throwProblem(@QueryParam("status") int status, @QueryParam("title") String title,
                             @QueryParam("detail") String detail) {
        throw new TestProblem(title, Status.valueOf(status), detail);
    }

    private static final class TestProblem extends AbstractThrowableProblem {
        TestProblem(String title, StatusType status, String detail) {
            super(null, title, status, detail, null, null);
        }
    }

    private static final class TestRuntimeException extends RuntimeException {
        TestRuntimeException(String message) {
            super(message, new RuntimeException("Root cause"));
        }
    }

}