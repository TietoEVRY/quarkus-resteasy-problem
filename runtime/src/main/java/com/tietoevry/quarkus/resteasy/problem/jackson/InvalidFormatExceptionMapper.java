package com.tietoevry.quarkus.resteasy.problem.jackson;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.tietoevry.quarkus.resteasy.problem.ExceptionMapperBase;
import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Response;

/**
 * Mapper for Jackson InvalidFormatException, which is more specialised version of JsonProcessingException
 */
@Priority(Priorities.USER)
public final class InvalidFormatExceptionMapper extends ExceptionMapperBase<InvalidFormatException> {

    @Override
    protected HttpProblem toProblem(InvalidFormatException exception) {
        return HttpProblem.builder()
                .withStatus(Response.Status.BAD_REQUEST)
                .withTitle(Response.Status.BAD_REQUEST.getReasonPhrase())
                .withDetail(exception.getOriginalMessage())
                .with("field", serializePath(exception.getPath()))
                .build();
    }

    private String serializePath(List<JsonMappingException.Reference> path) {
        String pathString = path.stream()
                .map(this::refToString)
                .collect(Collectors.joining());
        return removeFirstDot(pathString);
    }

    private String refToString(JsonMappingException.Reference ref) {
        if (ref.getFieldName() != null) {
            return "." + ref.getFieldName();
        }
        if (ref.getIndex() >= 0) {
            return "[" + ref.getIndex() + "]";
        }
        return ".?";
    }

    private String removeFirstDot(String field) {
        if (field.length() > 1) {
            return field.substring(1);
        } else {
            return "?";
        }
    }
}
