package org.nsu.fit.tests.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.AccountTokenPojo;
import org.nsu.fit.services.rest.data.CustomerPojo;
import org.nsu.fit.tests.ui.screen.LoginScreen;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TopUpBalanceTest extends BaseUITest {

    private static final int MONEY_TO_ADD = 777;

    private final CustomerPojo customer = apiClient.createAutoGeneratedCustomer(adminToken);
    private final AccountTokenPojo userToken = apiClient.authenticate(customer.login, customer.pass);

    @Test(description = "Top up balance")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("To up balance feature")
    public void topUpBalance() {
        new LoginScreen(browser)
                .loginAsCustomer(customer.login, customer.pass)
                .topUpBalance()
                .fillMoney(MONEY_TO_ADD)
                .clickSubmit();

        CustomerPojo currentCustomer = apiClient.getCurrentUserProfile(userToken);
        Assert.assertEquals(currentCustomer.balance, customer.balance + MONEY_TO_ADD);
    }
}