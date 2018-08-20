package com.banking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;

import com.banking.model.Account;
import com.banking.model.AccountType;
import com.banking.model.Transaction;
import com.banking.repository.CustomerRepository;

import static com.banking.TestConstants.PRIMARY_ACCOUNT_INDEX;
import static com.banking.TestConstants.SECONDARY_ACCOUNT_INDEX;;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {

	@Mock
    private AccountService accountService;
    
    @Mock
    private TransactionService transactionService;
 
    @MockBean
    private CustomerRepository customerRepositoryMock;
    
    @Test
    public void whenTransactionBetweenAccounts_thenReturnTransactions() {
    	
    	int customerId = 25;
    	BigDecimal accountBalance = new BigDecimal(200);
    	BigDecimal amount = new BigDecimal(100);
    	
    	Account primaryAccount = new Account(AccountType.PRIMARY, customerId, accountBalance);
    	Account secondaryAccount = new Account(AccountType.SECONDARY, customerId, accountBalance);
    	
    	transactionService.transactionBetweenAccounts(primaryAccount, secondaryAccount, amount);
    	
    	Transaction transactionForPrimaryAccount = new Transaction(primaryAccount, secondaryAccount.getId(), amount);
    	Transaction transactionForSecondaryAccount = new Transaction(secondaryAccount, primaryAccount.getId(), amount.negate());
    	
    	List<Transaction> transactions = new ArrayList<>();
    	transactions.add(transactionForPrimaryAccount);
    	transactions.add(transactionForSecondaryAccount);
    	
    	when(accountService.getAccountsTransaction(primaryAccount.getId())).thenReturn(transactions);
    	
    	Transaction foundTransactionForPrimaryAccount = accountService.getAccountsTransaction(primaryAccount.getId()).get(PRIMARY_ACCOUNT_INDEX);
    	Transaction foundTransactionForSecondaryAccount = accountService.getAccountsTransaction(primaryAccount.getId()).get(SECONDARY_ACCOUNT_INDEX);
    	     
         assertThat(foundTransactionForPrimaryAccount.getAccount().getId()).isEqualTo(foundTransactionForSecondaryAccount.getOtherPartyId());
     }
    
    @Test
    public void whenTransactionBetweenAccounts_thenUpdateBalanceUp() {
    	
    	int customerId = 25;
    	BigDecimal accountBalance = new BigDecimal(200);
    	BigDecimal amount = new BigDecimal(100);

    	Account primaryAccount = new Account(AccountType.PRIMARY, customerId, accountBalance);
    	Account secondaryAccount = new Account(AccountType.SECONDARY, customerId, new BigDecimal(0));
    	
    	BigDecimal expectedBalance = secondaryAccount.getBalance().add(amount);
    	
    	when(transactionService.transactionBetweenAccounts(primaryAccount, secondaryAccount, amount)).thenReturn(expectedBalance);
    	
    	transactionService.transactionBetweenAccounts(primaryAccount, secondaryAccount, amount);
    	     
         assertThat(secondaryAccount.getBalance().add(amount)).isEqualTo(expectedBalance);
     }
    
    @Test
    public void whenTransactionBetweenAccounts_thenUpdateBalanceDown() {
    	
    	int customerId = 25;
    	BigDecimal accountBalance = new BigDecimal(200);
    	BigDecimal amount = new BigDecimal(100);

    	Account primaryAccount = new Account(AccountType.PRIMARY, customerId, accountBalance);
    	Account secondaryAccount = new Account(AccountType.SECONDARY, customerId, new BigDecimal(0));
    	
    	BigDecimal expectedBalance = accountBalance.subtract(amount);
    	
    	when(transactionService.transactionBetweenAccounts(primaryAccount, secondaryAccount, amount)).thenReturn(expectedBalance);
    	
    	transactionService.transactionBetweenAccounts(primaryAccount, secondaryAccount, amount);
   
        assertThat(primaryAccount.getBalance().subtract(amount)).isEqualTo(expectedBalance);
     }
    
}
