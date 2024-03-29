package org.nsu.fit.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.CustomerPojo;
import org.nsu.fit.services.rest.data.PlanPojo;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TryToDeleteResourcesAsCustomer extends BaseITTest {

    @Test(description = "Check throws if try to delete plan as customer")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Access feature.")
    public void throwIfDeleteCustomerByCustomer() {
        PlanPojo plan = restClient.createPlan(fakerGenerator.generatePlan(), adminToken);
        Assert.assertThrows(
                RuntimeException.class,
                () -> restClient.deletePlan(
                        plan.getId(),
                        customerToken
                )
        );
    }

    @Test(description = "Check throws if try to delete customer as customer")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Access feature.")
    public void throwIfDeletePlanByCustomer() {
        CustomerPojo customer = restClient.createAutoGeneratedCustomer(adminToken);
        Assert.assertThrows(
                RuntimeException.class,
                () -> restClient.deleteCustomer(
                        customer.id,
                        customerToken
                )
        );
    }
}
