package org.nsu.fit.tests.ui.screen;

import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.shared.Screen;

public class CreateCustomerScreen extends Screen {
    public CreateCustomerScreen(Browser browser) {
        super(browser, "add-customer");
    }

    public CreateCustomerScreen fillEmail(String email) {
        fillInputByName("login", email);
        return this;
    }

    public CreateCustomerScreen fillPassword(String password) {
        fillInputByName("password", password);
//        fillFieldByName("password", "1");
        return this;
    }

    public CreateCustomerScreen fillFirstName(String firstName) {
        fillInputByName("firstName", firstName);
        return this;
    }

    public CreateCustomerScreen fillLastName(String lastName) {
        fillInputByName("lastName", lastName);
        return this;
    }

    public AdminScreen clickSubmit() {
        clickSubmitButton("Wrong arguments to create customer");
        return new AdminScreen(browser);
    }

    public AdminScreen clickCancel() {
        clickCancelButton();
        return new AdminScreen(browser);
    }
}
