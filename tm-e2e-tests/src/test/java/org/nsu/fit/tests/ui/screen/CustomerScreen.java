package org.nsu.fit.tests.ui.screen;

import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.shared.Screen;
import org.openqa.selenium.By;

public class CustomerScreen extends Screen {
    public CustomerScreen(Browser browser) {
        super(browser, "customer");
    }

    public TopUpBalanceScreen topUpBalance() {
        browser.click(By.linkText("Top up balance"));
        return new TopUpBalanceScreen(browser);
    }

    public CustomerScreen subscribeFirstPlan() {
        String planTableXPath = "(//*[@class='MuiTableBody-root'])[2]";
        browser.click(By.xpath(planTableXPath + "//button"));
        browser.click(By.xpath(planTableXPath + "//button[1]"));
        return this;
    }

    public CustomerScreen unsubscribeFirstPlan() {
        String subscriptionsTableXPath = "(//*[@class='MuiTableBody-root'])[1]";
        browser.click(By.xpath(subscriptionsTableXPath + "//button"));
        browser.click(By.xpath(subscriptionsTableXPath + "//button[1]"));
        return this;
    }

    public LoginScreen logout() {
        browser.click(By.linkText("Logout"));
        return new LoginScreen(browser);
    }
}
