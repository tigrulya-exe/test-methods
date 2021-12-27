package org.nsu.fit.services.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import org.nsu.fit.services.generator.FakerGenerator;
import org.nsu.fit.services.generator.UserGenerator;
import org.nsu.fit.services.rest.data.*;
import org.nsu.fit.shared.JsonMapper;

import java.util.List;
import java.util.UUID;

public class RestApiClient {
    // Note: change url if you want to use the docker compose.
//    private static final String REST_URI = "http://localhost:8080/tm-backend/rest";
    private static final String REST_URI = "http://localhost:8089/tm-backend/rest";

    private final HttpClient httpClient = new HttpClient(REST_URI);

    private final UserGenerator userGenerator = new FakerGenerator();

    public AccountTokenPojo authenticate(String login, String pass) {
        CredentialsPojo credentialsPojo = new CredentialsPojo(login, pass);

        return httpClient.post(
                "authenticate",
                JsonMapper.toJson(credentialsPojo, true),
                AccountTokenPojo.class,
                null
        );
    }

    public CustomerPojo createAutoGeneratedCustomer(AccountTokenPojo accountToken) {
        return createCustomer(userGenerator.generateCustomer(), accountToken);
    }

    public CustomerPojo createCustomer(CustomerPojo customerPojo, AccountTokenPojo accountToken) {
        return httpClient.post(
                "customers",
                JsonMapper.toJson(customerPojo, true),
                CustomerPojo.class,
                accountToken
        );
    }

    public void deleteCustomer(UUID id, AccountTokenPojo accountToken) {
        httpClient.delete(
                "customers/" + id,
                Void.class,
                accountToken
        );
    }

    public List<CustomerPojo> getCustomers(AccountTokenPojo accountToken) {
        return httpClient.get("customers", new TypeReference<List<CustomerPojo>>() {}, accountToken);
    }

    public PlanPojo createPlan(PlanPojo planPojo, AccountTokenPojo accountToken) {
        return httpClient.post(
                "plans",
                JsonMapper.toJson(planPojo, true),
                PlanPojo.class,
                accountToken
        );
    }

    public void deletePlan(UUID id, AccountTokenPojo accountToken) {
        httpClient.delete("plans/" + id, Void.class, accountToken);
    }

    public List<PlanPojo> getPlans(AccountTokenPojo accountToken) {
        return httpClient.get("plans",  new TypeReference<List<PlanPojo>>() {}, accountToken);
    }

    public List<PlanPojo> getAvailablePlans(AccountTokenPojo accountToken) {
        return httpClient.get("available_plans", new TypeReference<List<PlanPojo>>() {
        }, accountToken);
    }

    public PlanPojo getPlanByName(String name, AccountTokenPojo accountToken) {
        return getAvailablePlans(accountToken)
                .stream()
                .filter(plan -> name.equals(plan.getName()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public SubscriptionPojo createSubscription(SubscriptionPojo subPojo, AccountTokenPojo accountToken) {
        return httpClient.post(
                "subscriptions",
                JsonMapper.toJson(subPojo, true),
                SubscriptionPojo.class,
                accountToken
        );
    }

    public void deleteSubscription(UUID id, AccountTokenPojo accountToken) {
        httpClient.delete("subscriptions/" + id, Void.class, accountToken);
    }

    public List<SubscriptionPojo> getAvailableSubscriptions(AccountTokenPojo accountToken) {
        return httpClient.get("available_subscriptions", new TypeReference<List<SubscriptionPojo>>() {},
            accountToken);
    }

    public CustomerPojo getCurrentUserProfile(AccountTokenPojo accountToken) {
        return httpClient.get("me", CustomerPojo.class, accountToken);
    }
}
