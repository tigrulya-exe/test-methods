package org.nsu.fit.shared;

import org.nsu.fit.services.browser.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public class Screen {
    protected Browser browser;

    public Screen(Browser browser, String path) {
        this.browser = browser;

        if (!browser.waitPage()) {
            throw new IllegalStateException("Can't load page");
        }

        if (!browser.containsTitle(path)) {
            throw new IllegalStateException("Page not loaded");
        }
    }

    protected void clickSubmitButton(String errorMessage) {
        browser.click(By.xpath("//button[@type = 'submit']"));
        try {
            browser.waitForElement(By.xpath("//div[@role = 'alert']"), 1);
            throw new IllegalArgumentException(errorMessage);
        } catch (TimeoutException ignored) {
        }
    }

    protected void clickCancelButton() {
        browser.click(By.id("back-button"));
    }

    protected void clickButtonByTitle(String title) {
        browser.click(By.xpath("//button[@title = '" + title + "']"));
    }

    protected void fillInputByName(String name, String newValue) {
        browser.typeText(
                By.xpath("//input[@name = '" + name + "']"),
                newValue
        );
    }
}
