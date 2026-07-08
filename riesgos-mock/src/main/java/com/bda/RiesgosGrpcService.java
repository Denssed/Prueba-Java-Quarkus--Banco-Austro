package com.bda;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import com.bda.riesgos.CedulaRequest;
import com.bda.riesgos.Deuda;
import com.bda.riesgos.DeudasResponse;
import com.bda.riesgos.RiesgosService;
import com.bda.riesgos.ScoreResponse;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class RiesgosGrpcService implements RiesgosService {

    private static final Duration SCORE_DELAY = Duration.ofSeconds(2);
    private static final Duration DEUDAS_DELAY = Duration.ofMillis(1500);

    private final Random random = new Random();

    @Override
    public Uni<ScoreResponse> getScore(CedulaRequest request) {
        int score = random.nextInt(101);
        return Uni.createFrom().item(ScoreResponse.newBuilder().setScore(score).build())
                .onItem().delayIt().by(SCORE_DELAY);
    }

    @Override
    public Uni<DeudasResponse> getDeudas(CedulaRequest request) {
        List<Deuda> deudas = List.of(
                Deuda.newBuilder().setEntidad("Banco Pichincha").setMensualidad(randomMensualidad()).build(),
                Deuda.newBuilder().setEntidad("Banco Guayaquil").setMensualidad(randomMensualidad()).build());
        return Uni.createFrom().item(DeudasResponse.newBuilder().addAllDeudas(deudas).build())
                .onItem().delayIt().by(DEUDAS_DELAY);
    }

    private double randomMensualidad() {
        return Math.round((50 + random.nextDouble() * 200) * 100.0) / 100.0;
    }
}
