package com.tietoevry.quarkus.resteasy.problem.security;

import static org.zalando.problem.Status.UNAUTHORIZED;

import com.tietoevry.quarkus.resteasy.problem.ExceptionMapperBase;
import io.quarkus.security.AuthenticationFailedException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import org.zalando.problem.Problem;

/**
 * Mapper overriding default Quarkus exception mapper to make all error responses compliant with RFC7807.
 *
 * @see io.quarkus.resteasy.runtime.AuthenticationFailedExceptionMapper
 */
@Priority(Priorities.USER)
public final class AuthenticationFailedExceptionMapper extends ExceptionMapperBase<AuthenticationFailedException> {

    @Override
    protected Problem toProblem(AuthenticationFailedException exception) {
        return Problem.valueOf(UNAUTHORIZED, exception.getMessage());
    }

}
