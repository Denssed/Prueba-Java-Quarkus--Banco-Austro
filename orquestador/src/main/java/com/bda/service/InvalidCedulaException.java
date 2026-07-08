package com.bda.service;

public class InvalidCedulaException extends RuntimeException {

    public InvalidCedulaException(String cedula) {
        super("Cédula inválida: " + cedula);
    }
}
