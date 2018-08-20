package com.banking.controller;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.banking.model.Account;
import com.banking.model.AccountType;
import com.banking.model.CustomerDetail;
import com.banking.model.Transaction;
import com.banking.service.CustomerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @After
    public void cleanUp() throws Exception {
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void should_return_customer_details() throws Exception {
    	
    	int customerId = 1;
    	BigDecimal transferedAmount = new BigDecimal(10);
    	BigDecimal balance = new BigDecimal(580);
    	Account account = new Account(AccountType.PRIMARY, 1, new BigDecimal(300));
    	Transaction transaction = new Transaction(account, account.getId(), transferedAmount);
    	List<Transaction> transactions = new ArrayList<>();
    	transactions.add(transaction);
        CustomerDetail expected = new CustomerDetail("Michael", "Schumacher", balance, transactions);

        when(customerService.getCustomerDetail(customerId)).thenReturn(expected);

        CustomerDetail customerDetail = customerController.getCustomerDetail(customerId);

        assertThat(customerDetail, is(expected));
        verify(customerService).getCustomerDetail(customerId);
    }

}
