package com.bda.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.bda.entity.CreditEvaluation;

public record CreditEvaluationResponse(
        Long id,
        String cedula,
        BigDecimal monto,
        int plazo,
        BigDecimal salario,
        int score,
        BigDecimal deudaMensualTotal,
        BigDecimal cuota,
        boolean aprobado,
        Instant fechaEvaluacion) {

    public static CreditEvaluationResponse from(CreditEvaluation entity) {
        return new CreditEvaluationResponse(
                entity.id,
                entity.cedula,
                entity.monto,
                entity.plazo,
                entity.salario,
                entity.score,
                entity.deudaMensualTotal,
                entity.cuota,
                entity.aprobado,
                entity.fechaEvaluacion);
    }
}
