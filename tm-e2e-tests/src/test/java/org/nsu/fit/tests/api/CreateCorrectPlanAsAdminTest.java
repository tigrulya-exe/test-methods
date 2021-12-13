package org.nsu.fit.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.PlanPojo;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateCorrectPlanAsAdminTest extends BaseITTest {

    @Test(description = "Create correct customer as admin")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Plan feature.")
    public void createCorrectPlan() {
        PlanPojo before = fakerGenerator.generatePlan();
        PlanPojo after = restClient.createPlan(before, adminToken);
        Assert.assertEquals(before.getName(), after.getName());
    }
}
