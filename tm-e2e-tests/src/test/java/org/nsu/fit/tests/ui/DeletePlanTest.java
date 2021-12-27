package org.nsu.fit.tests.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.PlanPojo;
import org.nsu.fit.tests.ui.screen.LoginScreen;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;

public class DeletePlanTest extends BaseUITest {

    @Test(description = "Delete plan via UI.")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Delete plan feature")
    public void deletePlan() throws Exception {
        apiClient.createPlan(fakerGenerator.generatePlan(), adminToken);
        List<PlanPojo> plansBefore = apiClient.getPlans(adminToken);

        new LoginScreen(browser)
            .loginAsAdmin()
            .deleteFirstPlanAndConfirm();

        Thread.sleep(1000);
        List<PlanPojo> plansAfter = apiClient.getPlans(adminToken);

        Assert.assertEquals(plansBefore.size(), plansAfter.size() + 1);
    }

    @Test(description = "Cancel plan deleting via UI.")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Cancel plan delete feature")
    public void deletePlanCancel() throws Exception {
        List<PlanPojo> plansBefore = apiClient.getPlans(adminToken);

        new LoginScreen(browser)
            .loginAsAdmin()
            .deleteLastPlan()
            .cancel();

        Thread.sleep(1000);
        List<PlanPojo> plansAfter = apiClient.getPlans(adminToken);

        Assert.assertEquals(plansBefore.size(), plansAfter.size());
    }
}
