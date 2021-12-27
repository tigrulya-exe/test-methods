package org.nsu.fit.tests.ui.screen;

import lombok.SneakyThrows;
import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.shared.Screen;
import org.openqa.selenium.By;

public class AdminScreen extends Screen {
    public AdminScreen(Browser browser) {
        super(browser, "admin");
    }

    public CreateCustomerScreen createCustomer() {
        clickButtonByTitle("Add Customer");
        return new CreateCustomerScreen(browser);
    }

    public ConfirmActionScreen deleteFirstCustomer() {
        clickButtonByTitle("Delete", 1);
        return new ConfirmActionScreen(browser);
    }

    public AdminScreen deleteFirstCustomerAndConfirm() {
        String tableXPath = "(//*[@class='MuiTableBody-root'])[1]";
        browser.click(By.xpath(tableXPath + "//button"));
        browser.click(By.xpath(tableXPath + "//button[1]"));
        return this;
    }

    public AdminScreen searchInCustomers(String keyWord) {
        browser.typeText(
            By.xpath("//input[@placeholder = 'Search']"),
            keyWord
        );
        return this;
    }

    @SneakyThrows
    public boolean isElementPresent(String keyWord) {
        return browser.isElementPresent(By.xpath("//td[@value='" + keyWord + "']"));
    }

    public CreatePlanScreen createPlan() {
        clickButtonByTitle("Add plan");
        return new CreatePlanScreen(browser);
    }

    public ConfirmActionScreen deleteLastPlan() {
        clickButtonByTitle("Delete", "last()");
        return new ConfirmActionScreen(browser);
    }

    public AdminScreen deleteFirstPlanAndConfirm() {
        String tableXPath = "(//*[@class='MuiTableBody-root'])[2]";
        browser.click(By.xpath(tableXPath + "//button"));
        browser.click(By.xpath(tableXPath + "//button[1]"));
        return this;
    }

    public LoginScreen logout() {
        browser.click(By.linkText("Logout"));
        return new LoginScreen(browser);
    }

    public AdminScreen searchInPlans(String searchKeyWord) {
        browser.typeTextInLast(
            By.xpath("//input[@placeholder = 'Search']"),
            searchKeyWord
        );
        return this;
    }
}
