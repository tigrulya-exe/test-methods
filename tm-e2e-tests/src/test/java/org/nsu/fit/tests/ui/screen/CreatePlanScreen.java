package org.nsu.fit.tests.ui.screen;

import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.shared.Screen;
import org.openqa.selenium.By;

public class CreatePlanScreen extends Screen {
    public CreatePlanScreen(Browser browser) {
        super(browser, "add-plan");
    }

    public CreatePlanScreen fillName(String name) {
        fillInputByName("name", name);
        return this;
    }

    public CreatePlanScreen fillDetails(String details) {
        fillInputByName("details", details);
//        TODO: это чтобы если шо показать, что мы ошибки хендлим
//        fillFieldByName("password", "1");
        return this;
    }

    public CreatePlanScreen fillFee(int fee) {
        fillInputByName("fee", String.valueOf(fee));
        return this;
    }

    public AdminScreen clickSubmit() {
        clickSubmitButton("Wrong arguments to create plan");
        return new AdminScreen(browser);
    }

    public AdminScreen clickCancel() {
        clickCancelButton();
        return new AdminScreen(browser);
    }
}
