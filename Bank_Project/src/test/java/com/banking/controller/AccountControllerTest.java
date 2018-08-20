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
import com.banking.service.AccountService;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;
    
	
	public static final int customerId = 1;
	public static final BigDecimal balance = new BigDecimal(100);

    @After
    public void cleanUp() throws Exception {
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void should_create_and_return_secondary_account() throws Exception {
    	
        Account expected = new Account(AccountType.PRIMARY, customerId, balance);

        when(accountService.createSecondaryAccount(customerId, balance)).thenReturn(expected);

        Account account = accountController.createSecondaryAccount(customerId, balance);

        // Assert and verify
        assertThat(account, is(expected));
        verify(accountService).createSecondaryAccount(customerId, balance);
    }

}
