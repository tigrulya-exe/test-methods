package org.nsu.fit.tests.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.AccountTokenPojo;
import org.nsu.fit.services.rest.data.PlanPojo;
import org.nsu.fit.tests.ui.screen.LoginScreen;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreatePlanTest extends BaseUITest {

    @Test(description = "Create plan via UI.")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Create plan feature")
    public void createPlan() {
        AccountTokenPojo adminToken = apiClient.authenticate("admin", "setup");
        PlanPojo expected = fakerGenerator.generatePlan();

        new LoginScreen(browser)
                .loginAsAdmin()
                .createPlan()
                .fillDetails(expected.getDetails())
                .fillFee(expected.getFee())
                .fillName(expected.getName())
                .clickSubmit();

        PlanPojo actual = apiClient.getPlanByName(expected.getName(), adminToken);
        Assert.assertEquals(expected.getDetails(), actual.getDetails());
        Assert.assertEquals(expected.getFee(), actual.getFee());
    }
}
