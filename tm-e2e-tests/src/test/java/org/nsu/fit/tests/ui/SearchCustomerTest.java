package org.nsu.fit.tests.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.nsu.fit.services.rest.data.CustomerPojo;
import org.nsu.fit.tests.ui.screen.LoginScreen;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class SearchCustomerTest extends BaseUITest {
    private static final String SEARCH_KEY_WORD = "@bracadabra";

    private CustomerPojo testCustomer = fakerGenerator.generateCustomer();

    @BeforeTest
    public void createCustomer() {
        testCustomer.firstName = SEARCH_KEY_WORD;
        testCustomer = apiClient.createCustomer(testCustomer, adminToken);
    }

    @BeforeTest
    public void deleteCustomer() {
        apiClient.deleteCustomer(testCustomer.id, adminToken);
    }

    @Test(description = "Successful search in customers table")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Search in customers feature")
    public void searchCustomersSuccess() {
        boolean elementPresent = new LoginScreen(browser)
            .loginAsAdmin()
            .searchInCustomers(SEARCH_KEY_WORD)
            .isElementPresent(SEARCH_KEY_WORD);

        Assert.assertTrue(elementPresent);
    }

    @Test(description = "Unsuccessful search in customers table")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Search in customers feature")
    @Ignore
    public void searchCustomersFail() {
        boolean elementPresent = new LoginScreen(browser)
            .loginAsAdmin()
            .searchInCustomers("testSearch")
            .isElementPresent(SEARCH_KEY_WORD);

        Assert.assertFalse(elementPresent);
    }
}
