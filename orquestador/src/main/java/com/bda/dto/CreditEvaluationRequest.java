package com.bda.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreditEvaluationRequest(
        @NotNull @Pattern(regexp = "\\d{10}", message = "La cédula debe tener 10 dígitos") String cedula,
        @NotNull @Positive BigDecimal monto,
        @Positive int plazo,
        @NotNull @Positive BigDecimal salario) {
}
