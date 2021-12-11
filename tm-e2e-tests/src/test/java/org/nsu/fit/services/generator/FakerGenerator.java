package org.nsu.fit.services.generator;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import org.nsu.fit.services.rest.data.CustomerPojo;
import org.nsu.fit.services.rest.data.PlanPojo;

import java.util.UUID;

public class FakerGenerator implements UserGenerator {

    private final Faker faker = new Faker();

    @Override
    public CustomerPojo generateCustomer() {
        Name nameGenerator = faker.name();
        Internet netStaffGenerator = faker.internet();

        CustomerPojo contactPojo = new CustomerPojo();
        contactPojo.firstName = nameGenerator.firstName();
        contactPojo.lastName = nameGenerator.lastName();
        contactPojo.login = netStaffGenerator.emailAddress();
        contactPojo.pass = netStaffGenerator.password(6, 12);
        contactPojo.balance = 0;

        return contactPojo;
    }

    public PlanPojo generatePlan() {
        return PlanPojo.builder()
                .id(UUID.randomUUID())
                .details("details str")
                .fee(300)
                .name("name-" + UUID.randomUUID().toString().substring(0, 6))
                .build();
    }
}
