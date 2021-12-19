package org.nsu.fit.tests.ui.screen;

import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.shared.Screen;
import org.openqa.selenium.By;

public class LoginScreen extends Screen {
    public LoginScreen(Browser browser) {
        super(browser, "");
    }

    public AdminScreen loginAsAdmin() {
        login("admin", "setup");
        return new AdminScreen(browser);
    }

    public CustomerScreen loginAsCustomer(String userName, String password) {
        login(userName, password);
        return new CustomerScreen(browser);
    }

    private void login(String userName, String password) {
        browser.waitForElement(By.id("email"));

        browser.typeText(By.id("email"), userName);
        browser.typeText(By.id("password"), password);

        browser.click(By.xpath("//button[@type = 'submit']"));
    }
}
