package com.bda.client;

import java.math.BigDecimal;

import com.bda.riesgos.CedulaRequest;
import com.bda.riesgos.RiesgosService;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RiesgosGrpcClient {

    @GrpcClient("riesgos")
    RiesgosService riesgosService;

    public Uni<RiesgoResult> evaluarRiesgo(String cedula) {
        CedulaRequest request = CedulaRequest.newBuilder().setCedula(cedula).build();
        Uni<Integer> scoreUni = riesgosService.getScore(request).map(r -> r.getScore());
        Uni<BigDecimal> deudaUni = riesgosService.getDeudas(request)
                .map(r -> r.getDeudasList().stream()
                        .map(d -> BigDecimal.valueOf(d.getMensualidad()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        return Uni.combine().all().unis(scoreUni, deudaUni)
                .asTuple()
                .map(tuple -> new RiesgoResult(tuple.getItem1(), tuple.getItem2()));
    }
}
