package com.bda.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Modulo10ValidatorTest {

    @Test
    void aceptaCedulaValida() {
        assertTrue(Modulo10Validator.esValida("1710034065"));
    }

    @Test
    void rechazaDigitoVerificadorIncorrecto() {
        assertFalse(Modulo10Validator.esValida("1710034060"));
    }

    @Test
    void rechazaProvinciaInvalida() {
        assertFalse(Modulo10Validator.esValida("9910034065"));
    }

    @Test
    void rechazaLongitudIncorrecta() {
        assertFalse(Modulo10Validator.esValida("12345"));
    }

    @Test
    void rechazaTercerDigitoMayorOIgualASeis() {
        assertFalse(Modulo10Validator.esValida("1760034065"));
    }
}
