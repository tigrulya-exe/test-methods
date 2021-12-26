package org.nsu.fit.tests.ui.screen;

import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.shared.Screen;
import org.openqa.selenium.By;

public class AdminScreen extends Screen {
    public AdminScreen(Browser browser) {
        super(browser, "admin");
    }

    public CreateCustomerScreen createCustomer() {
        browser.click(By.xpath("//button[@title = 'Add Customer']"));
        return new CreateCustomerScreen(browser);
    }

    public CreatePlanScreen createPlan() {
        browser.click(By.xpath("//button[@title = 'Add plan']"));
        return new CreatePlanScreen(browser);
    }

    public LoginScreen logout() {
        browser.click(By.linkText("Logout"));
        return new LoginScreen(browser);
    }
}
