package org.nsu.fit.tm_backend.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.nsu.fit.tm_backend.database.data.CustomerPojo;
import org.nsu.fit.tm_backend.database.data.SubscriptionPojo;
import org.nsu.fit.tm_backend.manager.CustomerManager;
import org.nsu.fit.tm_backend.manager.SubscriptionManager;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

// Лабораторная 2: покрыть юнит тестами класс StatisticOperation на 100%.
public class StatisticOperationTest {
    @Mock
    private CustomerManager customerManager;
    @Mock
    private SubscriptionManager subscriptionManager;
    @Spy
    private List<UUID> customerIds = Stream.of(
        UUID.randomUUID(),
        UUID.randomUUID()
    ).collect(Collectors.toList());

    @InjectMocks
    private StatisticOperation statisticOperation;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstructor() {
        testConstructorExceptionMessage(null, subscriptionManager, customerIds, "customerManager");
        testConstructorExceptionMessage(customerManager, null, customerIds, "subscriptionManager");
        testConstructorExceptionMessage(customerManager, subscriptionManager, null, "customerIds");
    }

    @Test
    void testExecute() {
        when(customerManager.getCustomer(customerIds.get(0)))
            .thenReturn(CustomerPojo.builder().balance(1).build());
        when(customerManager.getCustomer(customerIds.get(1)))
            .thenReturn(CustomerPojo.builder().balance(2).build());
        when(subscriptionManager.getSubscriptions(customerIds.get(0)))
            .thenReturn(List.of(SubscriptionPojo.builder().planFee(100).build()));
        when(subscriptionManager.getSubscriptions(customerIds.get(1)))
            .thenReturn(List.of(
                SubscriptionPojo.builder().planFee(200).build(),
                SubscriptionPojo.builder().planFee(300).build()
            ));

        StatisticOperation.StatisticOperationResult result = statisticOperation.Execute();
        assertEquals(3, result.overallBalance);
        assertEquals(600, result.overallFee);

        verify(customerManager).getCustomer(customerIds.get(0));
        verify(customerManager).getCustomer(customerIds.get(1));
        verify(customerManager, times(2)).getCustomer(any());
        verify(subscriptionManager).getSubscriptions(customerIds.get(0));
        verify(subscriptionManager).getSubscriptions(customerIds.get(1));
        verify(subscriptionManager, times(2)).getSubscriptions(any());
        verifyNoMoreInteractions(customerManager);
        verifyNoMoreInteractions(subscriptionManager);
    }

    private void testConstructorExceptionMessage(
        CustomerManager customerManager,
        SubscriptionManager subscriptionManager,
        List<UUID> customerIds,
        String expectedMessage) {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new StatisticOperation(customerManager, subscriptionManager, customerIds)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }
}
