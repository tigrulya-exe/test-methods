package org.nsu.fit.tests.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.AccountTokenPojo;
import org.nsu.fit.services.rest.data.CustomerPojo;
import org.nsu.fit.tests.ui.screen.LoginScreen;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateCustomerTest extends BaseUITest {

    @Test(description = "Create customer via UI.")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Create customer feature")
    public void createCustomer() {
        CustomerPojo expected = fakerGenerator.generateCustomer();

        new LoginScreen(browser)
                .loginAsAdmin()
                .createCustomer()
                .fillEmail(expected.login)
                .fillPassword(expected.pass)
                .fillFirstName(expected.firstName)
                .fillLastName(expected.lastName)
                .clickSubmit();

        AccountTokenPojo token = apiClient.authenticate(expected.login, expected.pass);
        CustomerPojo actual = apiClient.getCurrentUserProfile(token);

        Assert.assertEquals(actual.login, expected.login);
        Assert.assertEquals(actual.firstName, expected.firstName);
        Assert.assertEquals(actual.lastName, expected.lastName);
    }
}
