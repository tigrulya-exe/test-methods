package org.nsu.fit.tests.ui.screen;

import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.shared.Screen;

public class TopUpBalanceScreen extends Screen {
    public TopUpBalanceScreen(Browser browser) {
        super(browser, "top-up-balance");
    }

    public TopUpBalanceScreen fillMoney(int moneyToAdd) {
        fillInputByName("money", String.valueOf(moneyToAdd));
        return this;
    }

    public CustomerScreen clickSubmit() {
        clickSubmitButton("");
        return new CustomerScreen(browser);
    }

    public CustomerScreen clickCancel() {
        clickCancelButton();
        return new CustomerScreen(browser);
    }

}
