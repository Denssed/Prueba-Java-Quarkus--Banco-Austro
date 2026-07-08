package com.bda.resource;

import java.util.List;

import com.bda.dto.CreditEvaluationRequest;
import com.bda.dto.CreditEvaluationResponse;
import com.bda.service.CreditEvaluationService;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/credit-evaluations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreditEvaluationResource {

    private final CreditEvaluationService service;

    public CreditEvaluationResource(CreditEvaluationService service) {
        this.service = service;
    }

    @POST
    public Uni<CreditEvaluationResponse> evaluar(@Valid CreditEvaluationRequest request) {
        return service.evaluar(request);
    }

    @GET
    public List<CreditEvaluationResponse> listar() {
        return service.listar();
    }
}
