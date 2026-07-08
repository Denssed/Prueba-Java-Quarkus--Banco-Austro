package com.bda.service;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidCedulaExceptionMapper implements ExceptionMapper<InvalidCedulaException> {

    @Override
    public Response toResponse(InvalidCedulaException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }

    public record ErrorResponse(String mensaje) {
    }
}
