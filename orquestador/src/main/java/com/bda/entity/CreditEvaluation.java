package com.bda.entity;

import java.math.BigDecimal;
import java.time.Instant;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CreditEvaluation extends PanacheEntity {

    @Column(nullable = false, length = 10)
    public String cedula;

    @Column(nullable = false)
    public BigDecimal monto;

    @Column(nullable = false)
    public int plazo;

    @Column(nullable = false)
    public BigDecimal salario;

    @Column(nullable = false)
    public int score;

    @Column(name = "deuda_mensual_total", nullable = false)
    public BigDecimal deudaMensualTotal;

    @Column(nullable = false)
    public BigDecimal cuota;

    @Column(nullable = false)
    public boolean aprobado;

    @Column(name = "fecha_evaluacion", nullable = false)
    public Instant fechaEvaluacion;
}
