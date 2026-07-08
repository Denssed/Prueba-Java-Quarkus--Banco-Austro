package com.bda;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.bda.riesgos.CedulaRequest;
import com.bda.riesgos.RiesgosService;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RiesgosGrpcServiceTest {

    @GrpcClient
    RiesgosService riesgosService;

    @Test
    void getScoreReturnsValueInRange() {
        CedulaRequest request = CedulaRequest.newBuilder().setCedula("0102030405").build();
        int score = riesgosService.getScore(request).await().indefinitely().getScore();
        assertTrue(score >= 0 && score <= 100);
    }

    @Test
    void getDeudasReturnsNonEmptyList() {
        CedulaRequest request = CedulaRequest.newBuilder().setCedula("0102030405").build();
        var response = riesgosService.getDeudas(request).await().indefinitely();
        assertFalse(response.getDeudasList().isEmpty());
    }
}
