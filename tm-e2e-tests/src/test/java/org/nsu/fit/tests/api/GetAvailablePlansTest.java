package org.nsu.fit.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class GetAvailablePlansTest extends BaseITTest {

    @Test(description = "Get available plans")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Customer feature.")
    public void getAvailablePlans() {
        restClient.getAvailablePlans(customerToken);
    }
}
