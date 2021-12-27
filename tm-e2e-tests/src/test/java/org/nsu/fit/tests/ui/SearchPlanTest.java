package org.nsu.fit.tests.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.PlanPojo;
import org.nsu.fit.tests.ui.screen.LoginScreen;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class SearchPlanTest extends BaseUITest{
    private static final String SEARCH_KEY_WORD = "@bracadabra";

    private PlanPojo testPlan = fakerGenerator.generatePlan();

    @BeforeClass
    public void createPlan() {
        testPlan.setName(SEARCH_KEY_WORD);
        testPlan = apiClient.createPlan(testPlan, adminToken);
    }

    @AfterClass
    public void deletePlan() {
        apiClient.deletePlan(testPlan.getId(), adminToken);
    }

    @Test(description = "Successful search in Plans table")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Search in Plans feature")
    public void searchPlansSuccess() {
        boolean elementPresent = new LoginScreen(browser)
            .loginAsAdmin()
            .searchInPlans(SEARCH_KEY_WORD)
            .isElementPresent(testPlan.getDetails());

        Assert.assertTrue(elementPresent);
    }

    @Test(description = "Unsuccessful search in Plans table")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Search in Plans feature")
    @Ignore
    public void searchPlansFail() {
        boolean elementPresent = new LoginScreen(browser)
            .loginAsAdmin()
            .searchInPlans("testSearch")
            .isElementPresent(SEARCH_KEY_WORD);

        Assert.assertFalse(elementPresent);
    }
}
