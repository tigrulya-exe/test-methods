package org.nsu.fit.tm_backend.manager;

import org.slf4j.Logger;
import org.nsu.fit.tm_backend.database.IDBService;
import org.nsu.fit.tm_backend.database.data.PlanPojo;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlanManager extends ParentManager {
    public PlanManager(IDBService dbService, Logger flowLog) {
        super(dbService, flowLog);
    }

    /**
     * Метод создает новый объект типа Plan. Ограничения:
     * name - длина не больше 128 символов и не меньше 2 включительно не содержит спец символов. Имена не пересекаются друг с другом;
     * details - длина не больше 1024 символов и не меньше 1 включительно;
     * fee - больше либо равно 0 но меньше либо равно 5000.
     */
    public PlanPojo createPlan(PlanPojo plan) {
        if (plan == null) {
            throw new IllegalArgumentException("Argument 'plan' is null.");
        }

        if (plan.name == null) {
            throw new IllegalArgumentException("Field 'plan.name' is null.");
        }

        if (plan.name.length() < 2 || plan.name.length() > 128) {
            throw new IllegalArgumentException("Name's length should be more or equal 2 symbols and less or equal 128 symbols.");
        }

        if (lookupPlan(plan.name) != null) {
            throw new IllegalArgumentException("Name already in use.");
        }

        if (plan.fee < 0 || plan.fee > 5000 ) {
            throw new IllegalArgumentException("Fee should be more or equal 0 and less or equal 5000");
        }

        return dbService.createPlan(plan);
    }

    public void deletePlan(UUID id) {
        dbService.deletePlan(id);
    }

    public PlanPojo lookupPlan(String name) {
        return dbService.getPlans().stream()
                .filter(x -> x.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Возвращает список планов доступных для покупки или список всех планов в случае если customerId = null.
     */
    public List<PlanPojo> getPlans(UUID customerId) {
        List<UUID> usedPlanIds = customerId == null
                ? Collections.emptyList()
                : dbService.getSubscriptions(customerId).stream()
                    .map(s -> s.planId)
                    .collect(Collectors.toList());

        return dbService.getPlans().stream()
                .filter(plan -> !usedPlanIds.contains(plan.id))
                .collect(Collectors.toList());
    }
}
