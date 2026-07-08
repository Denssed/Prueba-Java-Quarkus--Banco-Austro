package com.bda.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

import com.bda.client.RiesgoResult;
import com.bda.client.RiesgosGrpcClient;
import com.bda.dto.CreditEvaluationRequest;
import com.bda.dto.CreditEvaluationResponse;
import com.bda.entity.CreditEvaluation;
import com.bda.repository.CreditEvaluationRepository;
import com.bda.validator.Modulo10Validator;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreditEvaluationService {

    private static final BigDecimal TASA_ANUAL = new BigDecimal("0.15");
    private static final BigDecimal LIMITE_ENDEUDAMIENTO = new BigDecimal("0.40");
    private static final int SCORE_MINIMO = 70;

    private final RiesgosGrpcClient riesgosGrpcClient;
    private final CreditEvaluationRepository repository;

    public CreditEvaluationService(RiesgosGrpcClient riesgosGrpcClient, CreditEvaluationRepository repository) {
        this.riesgosGrpcClient = riesgosGrpcClient;
        this.repository = repository;
    }

    public Uni<CreditEvaluationResponse> evaluar(CreditEvaluationRequest request) {
        if (!Modulo10Validator.esValida(request.cedula())) {
            return Uni.createFrom().failure(new InvalidCedulaException(request.cedula()));
        }
        return riesgosGrpcClient.evaluarRiesgo(request.cedula())
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(riesgo -> construirYPersistir(request, riesgo));
    }

    @Transactional
    CreditEvaluationResponse construirYPersistir(CreditEvaluationRequest request, RiesgoResult riesgo) {
        BigDecimal cuota = calcularCuota(request.monto(), request.plazo());
        boolean aprobado = riesgo.score() > SCORE_MINIMO
                && riesgo.deudaMensualTotal().add(cuota)
                        .compareTo(request.salario().multiply(LIMITE_ENDEUDAMIENTO)) < 0;

        CreditEvaluation entity = new CreditEvaluation();
        entity.cedula = request.cedula();
        entity.monto = request.monto();
        entity.plazo = request.plazo();
        entity.salario = request.salario();
        entity.score = riesgo.score();
        entity.deudaMensualTotal = riesgo.deudaMensualTotal();
        entity.cuota = cuota;
        entity.aprobado = aprobado;
        entity.fechaEvaluacion = Instant.now();
        repository.persist(entity);

        return CreditEvaluationResponse.from(entity);
    }

    public List<CreditEvaluationResponse> listar() {
        return repository.listOrderedByFechaDesc().stream()
                .map(CreditEvaluationResponse::from)
                .toList();
    }

    private BigDecimal calcularCuota(BigDecimal monto, int plazo) {
        double i = TASA_ANUAL.doubleValue() / 12;
        int n = plazo * 12;
        double cuota = monto.doubleValue() * i / (1 - Math.pow(1 + i, -n));
        return BigDecimal.valueOf(cuota).setScale(2, RoundingMode.HALF_UP);
    }
}
