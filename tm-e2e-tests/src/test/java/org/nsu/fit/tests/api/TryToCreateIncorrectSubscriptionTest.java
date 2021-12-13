package org.nsu.fit.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.SubscriptionPojo;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TryToCreateIncorrectSubscriptionTest extends BaseITTest {

    @Test(description = "Create correct subscription")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Customer feature.")
    public void createCorrectCustomer() {
        SubscriptionPojo subPojo = fakerGenerator.generateSubscription();
        subPojo.setPlanFee(5000);
        Assert.assertThrows(
            RuntimeException.class,
            () -> restClient.createSubscription(
                subPojo,
                customerToken)
        );
    }
}
