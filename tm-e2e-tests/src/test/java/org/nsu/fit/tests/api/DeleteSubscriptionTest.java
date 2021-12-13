package org.nsu.fit.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.SubscriptionPojo;
import org.testng.annotations.Test;

public class DeleteSubscriptionTest extends BaseITTest {

    @Test(description = "Delete subscription")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Customer feature.")
    public void createCorrectCustomer() {
        SubscriptionPojo subPojo = restClient.createSubscription(fakerGenerator.generateSubscription(), customerToken);
        restClient.deleteSubscription(subPojo.id, customerToken);
    }
}
