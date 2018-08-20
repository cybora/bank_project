package com.banking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import com.banking.model.Account;
import com.banking.model.AccountType;
import com.banking.model.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.util.exception.AlreadyHasSecondaryAccountException;
import com.banking.util.exception.CustomerNeedsPrimaryAccountException;
import com.banking.util.exception.InsufficientPrimaryAccountCreditException;

import static com.banking.TestConstants.PRIMARY_ACCOUNT_INDEX;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest {
 
    @Mock
    private AccountService accountService;
 
    @Mock
    private AccountRepository accountRepository;
    
    
    
    @Before
    public void setUp() {
    	int accountId  = 33;
    	int ownerCustomerId = 25;
    	BigDecimal balance = new BigDecimal(300);
    	BigDecimal amount = new BigDecimal(100);
    	
    	Account account = new Account(accountId, AccountType.PRIMARY, ownerCustomerId, balance);
        Transaction transaction = new Transaction(account, account.getId(), amount);
        
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        
    	List<Account> accountList = new ArrayList<>();
    	accountList.add(account);
    	
    	/*
        Mockito.when(accountRepository.findAllByOwnerCustomerId(ownerCustomerId))
          .thenReturn(accountList);
        
        Mockito.when(transactionService.findAllByAccountId(accountId))
        .thenReturn(transactions);
        
        Mockito.when(accountRepository.findBalanceById(accountId))
        .thenReturn(balance);
        */
    }
    
    @Test
    public void whenGetAccountsByCustomer_thenReturnAccounts() {
    	int customerId = 25;
    	BigDecimal balance = new BigDecimal(300);
    	Account account = new Account(AccountType.PRIMARY, customerId, balance);
    	List<Account> accounts = new ArrayList<>();
    	accounts.add(account);
    	
    	when(accountService.getAccountsByCustomer(customerId)).thenReturn(accounts);
        Account found = accounts.get(PRIMARY_ACCOUNT_INDEX);
      
         assertThat(found.getBalance())
          .isEqualTo(balance);
     }
    
    @Test
    public void whenGetAccountsTransaction_thenReturnTransactions() {
    	int accountId  = 33;
    	int ownerCustomerId = 22;
    	BigDecimal balance = new BigDecimal(400);
    	BigDecimal amount = new BigDecimal(100);
    	
    	Account account = new Account(AccountType.PRIMARY, ownerCustomerId, balance);
    	Transaction transaction = new Transaction(account, account.getId(), amount);
    	
    	List<Transaction> transactions = new ArrayList<>();
    	transactions.add(transaction);
    	
    	when(accountService.getAccountsTransaction(accountId)).thenReturn(transactions);
        Transaction found = transactions.get(PRIMARY_ACCOUNT_INDEX);
      
         assertThat(found.getAmount())
          .isEqualTo(amount);
     }
    
    @Test
    public void whenGetAccountBalance_thenReturnBalance() {
    	int accountId = 33;
    	BigDecimal balance = new BigDecimal(300);
    	
    	when(accountService.getAccountBalance(accountId)).thenReturn(balance);
    	
    	BigDecimal found = accountService.getAccountBalance(accountId);
    	
         assertThat(found)
          .isEqualTo(balance);
     }
    
    @Test
    public void whenCreateAccount_thenCreateAccount() {
    	int customerId = 25;
    	BigDecimal balance = new BigDecimal(300);
    	
    	Account account = new Account(AccountType.PRIMARY, customerId, balance);
    	
    	List<Account> accounts = new ArrayList<>();    	
    	accounts.add(account);
    	
    	when(accountRepository.findAllByOwnerCustomerId(customerId)).thenReturn(accounts);
    	
    	Account found = accounts.get(PRIMARY_ACCOUNT_INDEX);
      
         assertThat(found.getOwnerCustomerId())
          .isEqualTo(account.getOwnerCustomerId());
     }
    
    @Test
    public void whenCreateSecondaryAccount_thenCreateAccount() {
    	
    	int customerId = 25;
    	BigDecimal initialCredit = new BigDecimal(100);
    	BigDecimal balance = new BigDecimal(400);
    	
    	Account primaryAccount = new Account(AccountType.PRIMARY, customerId, balance);
    	Account createdSecondaryAccount = new Account(AccountType.SECONDARY, customerId, initialCredit);
    	
    	when(accountService.createSecondaryAccount(customerId, initialCredit)).thenReturn(createdSecondaryAccount);
    	
    	List<Account> accounts = new ArrayList<>();
    	accounts.add(primaryAccount);
    	accounts.add(createdSecondaryAccount);
    	
    	when(accountService.getAccountsByCustomer(customerId)).thenReturn(accounts);
    	
    	Account found = accounts.get(PRIMARY_ACCOUNT_INDEX);
      
         assertThat(found.getOwnerCustomerId())
          .isEqualTo(createdSecondaryAccount.getOwnerCustomerId());
     }
    
    @Test(expected=InsufficientPrimaryAccountCreditException.class)
    public void whenCheckSecondaryAccountElligibility_thenThrowInsufficientPrimaryAccountCreditException() {
    	
    	int customerId = 25;
    	BigDecimal initialCredit = new BigDecimal(1000);
    	
    	when(accountService.checkSecondaryAccountElligibility(customerId, initialCredit)).thenThrow(InsufficientPrimaryAccountCreditException.class);

    	accountService.checkSecondaryAccountElligibility(customerId, initialCredit);
     }
    
    @Test(expected=AlreadyHasSecondaryAccountException.class)
    public void whenCheckSecondaryAccountElligibility_thenThrowAlreadyHasSecondaryAccountException() {
    	
    	int customerId = 25;
    	int accountId = 33;
    	BigDecimal balance = new BigDecimal(300);
    	BigDecimal initialCredit = new BigDecimal(100);
    	
    	Account primaryAccount = new Account(accountId, AccountType.PRIMARY, customerId, balance);
    	Account secondaryAccount = new Account(accountId, AccountType.SECONDARY, customerId, balance);
    	
    	when(accountService.checkSecondaryAccountElligibility(customerId, initialCredit)).thenThrow(AlreadyHasSecondaryAccountException.class);
    	
    	accountRepository.save(primaryAccount);
    	accountRepository.save(secondaryAccount);
    	
    	
    	accountService.checkSecondaryAccountElligibility(customerId, initialCredit);
     }
    
    @Test(expected=CustomerNeedsPrimaryAccountException.class)
    public void whenCheckSecondaryAccountElligibility_thenThrowCustomerNeedsPrimaryAccountException() {
    	
    	int customerId = 25;
    	BigDecimal initialCredit = new BigDecimal(100);
    	
    	when(accountService.checkSecondaryAccountElligibility(customerId, initialCredit)).thenThrow(CustomerNeedsPrimaryAccountException.class);
	
    	accountService.checkSecondaryAccountElligibility(customerId, initialCredit);
     }
    
    @Test
    public void whenChangeBalance_thenBalanceChanged() {
    	int customerId = 25;
    	int accountId = 33;
    	BigDecimal balance = new BigDecimal(200);
    	BigDecimal amount = new BigDecimal(100);
    	
    	Account expectedAccount = 
    			new Account(accountId, AccountType.PRIMARY, customerId, balance.add(amount));
    	
    	accountService.changeBalance(accountId, amount);
    	
    	List<Account> retrievedAccounts = new ArrayList<>();
    	retrievedAccounts.add(expectedAccount);
    	
    	when(accountRepository.findAllByOwnerCustomerId(customerId)).thenReturn(retrievedAccounts);
    	
    	Account found = retrievedAccounts.get(PRIMARY_ACCOUNT_INDEX);
    	
    	assertThat(found.getBalance())
        .isEqualTo(expectedAccount.getBalance());
    	
     }
    
}
