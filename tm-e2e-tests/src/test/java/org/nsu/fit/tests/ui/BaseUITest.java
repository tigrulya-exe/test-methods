package org.nsu.fit.tests.ui;

import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.services.browser.BrowserService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseUITest {
    protected Browser browser;

    @BeforeClass
    public void beforeClass() {
        System.out.println("before");
        browser = BrowserService.openNewBrowser();
    }

    @AfterClass
    public void afterClass() {
        System.out.println("after");
        browser.close();
    }
}
