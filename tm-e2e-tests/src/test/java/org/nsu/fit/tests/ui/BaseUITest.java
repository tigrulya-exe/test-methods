package org.nsu.fit.tests.ui;

import org.nsu.fit.services.browser.Browser;
import org.nsu.fit.services.browser.BrowserService;
import org.nsu.fit.services.generator.FakerGenerator;
import org.nsu.fit.services.rest.RestApiClient;
import org.nsu.fit.services.rest.data.AccountTokenPojo;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseUITest {

    protected Browser browser;

    protected final FakerGenerator fakerGenerator = new FakerGenerator();
    protected final RestApiClient apiClient = new RestApiClient();
    protected final AccountTokenPojo adminToken = apiClient.authenticate("admin", "setup");

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
