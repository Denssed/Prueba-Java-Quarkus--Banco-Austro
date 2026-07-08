package com.bda.repository;

import java.util.List;

import com.bda.entity.CreditEvaluation;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreditEvaluationRepository implements PanacheRepository<CreditEvaluation> {

    public List<CreditEvaluation> listOrderedByFechaDesc() {
        return list("ORDER BY fechaEvaluacion DESC");
    }
}
