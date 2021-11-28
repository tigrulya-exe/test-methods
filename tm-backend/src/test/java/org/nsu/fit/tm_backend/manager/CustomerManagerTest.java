package org.nsu.fit.tm_backend.manager;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nsu.fit.tm_backend.database.IDBService;
import org.nsu.fit.tm_backend.database.data.ContactPojo;
import org.nsu.fit.tm_backend.database.data.CustomerPojo;
import org.nsu.fit.tm_backend.database.data.TopUpBalancePojo;
import org.nsu.fit.tm_backend.manager.auth.data.AuthenticatedUserDetails;
import org.nsu.fit.tm_backend.shared.Authority;
import org.nsu.fit.tm_backend.shared.Globals;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Лабораторная 2: покрыть юнит тестами класс CustomerManager на 100%.
class CustomerManagerTest {

    private static final String CUSTOMER_LOGIN = "john_wick@example.com";
    private static final String ANOTHER_CUSTOMER_LOGIN = "david_wick@example.com";

    private static final int MONEY_TO_ADD = 777;
    private static final int ILLEGAL_MONEY_TO_ADD = -1;
    private static final int CUSTOMER_BALANCE = 0;

    private static final UUID CUSTOMER_ID = UUID.randomUUID();

    @Mock
    private Logger logger;

    @Mock
    private IDBService dbService;

    @InjectMocks
    private CustomerManager customerManager;

    private CustomerPojo testCustomer = createCustomer();

    @Getter
    private static Stream<Arguments> wrongCreateCustomerInputs = Stream.of(
            Arguments.of(null, "Argument 'customer' is null."),
            Arguments.of(
                    createCustomer().toBuilder().pass(null).build(),
                    "Field 'customer.pass' is null."
            ),
            Arguments.of(
                    createCustomer().toBuilder().pass("1").build(),
                    "Password's length should be more or equal 6 symbols and less or equal 12 symbols."
            ),
            Arguments.of(
                    createCustomer().toBuilder().pass("1234567890123").build(),
                    "Password's length should be more or equal 6 symbols and less or equal 12 symbols."
            ),
            Arguments.of(
                    createCustomer().toBuilder().pass("123qwe").build(),
                    "Password is very easy."
            ),
            Arguments.of(
                    createCustomer().toBuilder().balance(999).build(),
                    "Wrong balance."
            )
    );

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerPojo createCustomerOutput = createCustomer();
        createCustomerOutput.id = UUID.randomUUID();

        when(dbService.createCustomer(testCustomer)).thenReturn(createCustomerOutput);

        // Вызываем метод, который хотим протестировать
        CustomerPojo customer = customerManager.createCustomer(testCustomer);

        // Проверяем результат выполенния метода
        assertEquals(customer.id, createCustomerOutput.id);

        // Проверяем, что метод по созданию Customer был вызван ровно 1 раз с определенными аргументами
        verify(dbService).createCustomer(testCustomer);
    }

    @ParameterizedTest
    @MethodSource("getWrongCreateCustomerInputs")
    void testCreateCustomerWithInvalidFields(CustomerPojo inputCustomer, String expectedExceptionMessage) {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerManager.createCustomer(inputCustomer)
        );

        assertEquals(expectedExceptionMessage, exception.getMessage());
        verify(dbService, times(0)).createCustomer(any());
    }

    @Test
    void testCreateCustomerWithNonUniqueLogin() {
        when(dbService.getCustomers())
                .thenReturn(List.of(testCustomer));

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerManager.createCustomer(testCustomer)
        );

        assertEquals("Login already in use.", exception.getMessage());
        verify(dbService, times(0)).createCustomer(any());
    }

    @Test
    void testGetCustomers() {
        List<CustomerPojo> expected = List.of(testCustomer);

        when(dbService.getCustomers()).thenReturn(expected);
        List<CustomerPojo> result = customerManager.getCustomers();

        assertEquals(expected, result);

        verify(dbService).getCustomers();
        verifyNoMoreInteractions(dbService);
    }

    @Test
    void testGetCustomer() {
        CustomerPojo expected = createCustomer();
        expected.setId(CUSTOMER_ID);

        when(dbService.getCustomer(CUSTOMER_ID))
                .thenReturn(expected);
        CustomerPojo result = customerManager.getCustomer(CUSTOMER_ID);

        assertEquals(expected, result);

        verify(dbService).getCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(dbService);
    }

    @Test
    void testSuccessfulLookupCustomer() {
        CustomerPojo expected = createCustomer();

        when(dbService.getCustomers())
                .thenReturn(List.of(expected));
        CustomerPojo result = customerManager.lookupCustomer(CUSTOMER_LOGIN);

        assertEquals(expected, result);

        verify(dbService).getCustomers();
        verifyNoMoreInteractions(dbService);
    }

    @Test
    void testLookupCustomerMiss() {
        when(dbService.getCustomers())
                .thenReturn(List.of(testCustomer));
        CustomerPojo result = customerManager.lookupCustomer(ANOTHER_CUSTOMER_LOGIN);

        assertNull(result);

        verify(dbService).getCustomers();
        verifyNoMoreInteractions(dbService);
    }

    @Test
    void testDeleteCustomer() {
        customerManager.deleteCustomer(CUSTOMER_ID);

        verify(dbService).deleteCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(dbService);
    }

    @Test
    void testGetMeIfUserIsAdmin() {
        AuthenticatedUserDetails userDetails = new AuthenticatedUserDetails(
                CUSTOMER_ID.toString(),
                null,
                Set.of(Authority.ADMIN_ROLE)
        );

        ContactPojo result = customerManager.me(userDetails);

        assertEquals(Globals.ADMIN_LOGIN, result.login);
        assertNull(result.firstName);
        assertNull(result.lastName);

        verifyNoInteractions(dbService);
    }

    @Test
    void testGetMeIfUserIsNotAdmin() {
        AuthenticatedUserDetails userDetails = new AuthenticatedUserDetails(
                CUSTOMER_ID.toString(),
                CUSTOMER_LOGIN,
                Set.of()
        );

        when(dbService.getCustomerByLogin(CUSTOMER_LOGIN))
                .thenReturn(testCustomer);

        ContactPojo result = customerManager.me(userDetails);

        assertEquals(CUSTOMER_LOGIN, result.login);
        // will fail
        assertNull(result.firstName);
        assertNull(result.lastName);

        verify(dbService).getCustomerByLogin(CUSTOMER_LOGIN);
        verifyNoMoreInteractions(dbService);
    }

    @Test
    void testUpBalance() {
        TopUpBalancePojo topUpBalancePojo = new TopUpBalancePojo(CUSTOMER_ID, MONEY_TO_ADD);
        when(dbService.getCustomer(CUSTOMER_ID))
                .thenReturn(testCustomer);

        CustomerPojo result = customerManager.topUpBalance(topUpBalancePojo);

        assertEquals(CUSTOMER_BALANCE + MONEY_TO_ADD, result.getBalance());

        verify(dbService).getCustomer(CUSTOMER_ID);
        verify(dbService).editCustomer(testCustomer);
        verifyNoMoreInteractions(dbService);
    }

    @Test
    void testUpBalanceThrowsIfMoneyBelowZero() {
        TopUpBalancePojo topUpBalancePojo = new TopUpBalancePojo(CUSTOMER_ID, ILLEGAL_MONEY_TO_ADD);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerManager.topUpBalance(topUpBalancePojo)
        );

        assertEquals("Money to add should be positive", exception.getMessage());
        verifyNoInteractions(dbService);
    }

    private static CustomerPojo createCustomer() {
        return CustomerPojo.builder()
                .firstName("John")
                .lastName("Wick")
                .login(CUSTOMER_LOGIN)
                .pass("Baba_Jaga")
                .balance(CUSTOMER_BALANCE)
                .build();
    }
}
