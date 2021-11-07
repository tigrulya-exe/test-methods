package org.nsu.fit.tm_backend.manager;

import org.nsu.fit.tm_backend.database.IDBService;
import org.nsu.fit.tm_backend.database.data.ContactPojo;
import org.nsu.fit.tm_backend.database.data.CustomerPojo;
import org.nsu.fit.tm_backend.database.data.TopUpBalancePojo;
import org.nsu.fit.tm_backend.manager.auth.data.AuthenticatedUserDetails;
import org.nsu.fit.tm_backend.shared.Globals;
import org.slf4j.Logger;

import java.util.List;
import java.util.UUID;

public class CustomerManager extends ParentManager {
    public CustomerManager(IDBService dbService, Logger flowLog) {
        super(dbService, flowLog);
    }

    /**
     * Метод создает новый объект класса Customer. Ограничения:
     * Аргумент 'customer' - не null;
     * firstName - нет пробелов, длина от 2 до 12 символов включительно, начинается с заглавной буквы, остальные символы строчные, нет цифр и других символов;
     * lastName - нет пробелов, длина от 2 до 12 символов включительно, начинается с заглавной буквы, остальные символы строчные, нет цифр и других символов;
     * login - указывается в виде email, проверить email на корректность, проверить что нет customer с таким же email;
     * pass - длина от 6 до 12 символов включительно, не должен быть простым (123qwe или 1q2w3e), не должен содержать части login, firstName, lastName
     * balance - должно быть равно 0 перед отправкой базу данных.
     */
    public CustomerPojo createCustomer(CustomerPojo customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Argument 'customer' is null.");
        }

        if (customer.pass == null) {
            throw new IllegalArgumentException("Field 'customer.pass' is null.");
        }

        if (customer.pass.length() < 6 || customer.pass.length() > 12) {
            throw new IllegalArgumentException("Password's length should be more or equal 6 symbols and less or equal 12 symbols.");
        }

        if (customer.pass.equalsIgnoreCase("123qwe")) {
            throw new IllegalArgumentException("Password is very easy.");
        }

        if (lookupCustomer(customer.login) != null) {
            throw new IllegalArgumentException("Login already in use.");
        }

        if (customer.balance != 0) {
            throw new IllegalArgumentException("Wrong balance.");
        }

        return dbService.createCustomer(customer);
    }

    /**
     * Метод возвращает список customer'ов.
     */
    public List<CustomerPojo> getCustomers() {
        return dbService.getCustomers();
    }

    public CustomerPojo getCustomer(UUID customerId) {
        return dbService.getCustomer(customerId);
    }

    public CustomerPojo lookupCustomer(String login) {
        return dbService.getCustomers().stream()
                .filter(x -> x.login.equals(login))
                .findFirst()
                .orElse(null);
    }

    public ContactPojo me(AuthenticatedUserDetails authenticatedUserDetails) {
        ContactPojo contactPojo = new ContactPojo();

        if (authenticatedUserDetails.isAdmin()) {
            contactPojo.login = Globals.ADMIN_LOGIN;

            return contactPojo;
        }

        // Лабораторная 2: обратите внимание что вернули данных больше чем надо...
        // т.е. getCustomerByLogin честно возвратит все что есть в базе данных по этому customer'у.
        // необходимо написать такой unit тест, который бы отлавливал данное поведение.
        return dbService.getCustomerByLogin(authenticatedUserDetails.getName());
    }

    public void deleteCustomer(UUID id) {
        dbService.deleteCustomer(id);
    }

    /**
     * Метод добавляет к текущему баласу переданное значение, которое должно быть строго больше нуля.
     */
    public CustomerPojo topUpBalance(TopUpBalancePojo topUpBalancePojo) {

        if (topUpBalancePojo.money <= 0) {
            throw new IllegalArgumentException("Money to add should be positive");
        }

        CustomerPojo customerPojo = dbService.getCustomer(topUpBalancePojo.customerId);

        customerPojo.balance += topUpBalancePojo.money;

        dbService.editCustomer(customerPojo);

        return customerPojo;
    }
}
