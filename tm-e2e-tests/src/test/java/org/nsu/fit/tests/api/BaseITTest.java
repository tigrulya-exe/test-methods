package org.nsu.fit.tests.api;

import lombok.NoArgsConstructor;
import org.nsu.fit.services.generator.FakerGenerator;
import org.nsu.fit.services.rest.RestApiClient;
import org.nsu.fit.services.rest.data.AccountTokenPojo;
import org.testng.annotations.BeforeSuite;

@NoArgsConstructor
public class BaseITTest {
    protected static AccountTokenPojo adminToken;
    protected final RestApiClient restClient = new RestApiClient();
    protected FakerGenerator fakerGenerator = new FakerGenerator();

    @BeforeSuite
    public void init() {
        adminToken = restClient.authenticate("admin", "setup");
    }
}
