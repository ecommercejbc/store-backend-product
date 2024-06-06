package org.quarkus.business.config;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        StringBuilder sb = new StringBuilder();
        exception.getConstraintViolations().forEach(violation -> {
            sb.append(violation.getPropertyPath().toString())
                    .append(": ")
                    .append(violation.getMessage())
                    .append("\n");
        });
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(sb.toString())
                .build();
    }
}