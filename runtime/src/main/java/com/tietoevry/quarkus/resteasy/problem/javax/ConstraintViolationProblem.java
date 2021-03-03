package com.tietoevry.quarkus.resteasy.problem.javax;

import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class ConstraintViolationProblem implements Problem {

    private final Set<ConstraintViolation<?>> constraintViolations;

    public ConstraintViolationProblem(Set<ConstraintViolation<?>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }

    @Override
    public String getTitle() {
        return Status.BAD_REQUEST.getReasonPhrase();
    }

    @Override
    public StatusType getStatus() {
        return Status.BAD_REQUEST;
    }

    @Override
    public Map<String, Object> getParameters() {
        List<Violation> violations = constraintViolations
                .stream()
                .map(this::toViolation)
                .collect(Collectors.toList());
        return Map.of("violations", violations);
    }

    private Violation toViolation(ConstraintViolation<?> constraintViolation) {
        return new Violation(
                constraintViolation.getMessage(),
                dropFirstTwoPathElements(constraintViolation.getPropertyPath())
        );
    }

    private String dropFirstTwoPathElements(Path propertyPath) {
        Iterator<Path.Node> propertyPathIterator = propertyPath.iterator();
        propertyPathIterator.next();
        propertyPathIterator.next();

        List<String> allNamesExceptFirstTwo = new ArrayList<>();
        while (propertyPathIterator.hasNext()) {
            allNamesExceptFirstTwo.add(propertyPathIterator.next().getName());
        }

        return String.join(".", allNamesExceptFirstTwo);
    }

}