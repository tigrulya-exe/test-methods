package org.nsu.fit.tests.ui.screen;

import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.shared.Screen;

public class ConfirmActionScreen extends Screen {

    public ConfirmActionScreen(Browser browser) {
        super(browser, "admin");
    }

    public AdminScreen confirm() throws Exception {
        // TODO selenium doesn't find it wtf??
        clickButtonByTitle("Save"); //
        return new AdminScreen(browser);
    }

    public AdminScreen cancel() {
        // TODO this title exists in not all 'cancel' elements
        clickButtonByTitle("Cancel");
        return new AdminScreen(browser);
    }
}
