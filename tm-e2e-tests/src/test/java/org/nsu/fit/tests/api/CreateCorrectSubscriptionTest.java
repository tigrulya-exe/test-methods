package org.nsu.fit.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.SubscriptionPojo;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateCorrectSubscriptionTest extends BaseITTest {

    @Test(description = "Create correct subscription")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Customer feature.")
    public void createCorrectSubscription() {
        SubscriptionPojo before = fakerGenerator.generateSubscription();
        SubscriptionPojo after = restClient.createSubscription(before, customerToken);
        Assert.assertEquals(before.getPlanId(), after.getPlanId());
        Assert.assertEquals(before.getPlanDetails(), after.getPlanDetails());
    }
}
