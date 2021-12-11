package org.nsu.fit.tests.api;

import lombok.NoArgsConstructor;
import org.nsu.fit.services.generator.FakerGenerator;
import org.nsu.fit.services.rest.RestClient;
import org.nsu.fit.services.rest.data.AccountTokenPojo;
import org.testng.annotations.BeforeSuite;

@NoArgsConstructor
public class BaseITTest {
    protected final RestClient restClient = new RestClient();
    protected FakerGenerator fakerGenerator = new FakerGenerator();
    protected AccountTokenPojo adminToken;

    @BeforeSuite
    public void init() {
        adminToken = restClient.authenticate("admin", "setup");
    }
}
