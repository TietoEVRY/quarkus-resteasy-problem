package com.tietoevry.quarkus.resteasy.problem;

import javax.ws.rs.core.UriInfo;

/**
 * Context wrapper for everything ProblemPostProcessor implementations may need to do their job.
 * It can be easily extended without changing ProblemProcessor interface.
 *
 * @see ProblemPostProcessor
 */
class ProblemContext {

    /**
     * * Original exception caught by ExceptionMapper.
     */
    final Throwable cause;

    /**
     * UriInfo for currently handled HTTP request.
     */
    final UriInfo uriInfo;

    private ProblemContext(Throwable cause, UriInfo uriInfo) {
        this.cause = cause;
        this.uriInfo = uriInfo;
    }

    static ProblemContext of(Throwable exception, UriInfo uriInfo) {
        return new ProblemContext(exception, uriInfo);
    }
}