package com.banking.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.banking.model.Account;
import com.banking.model.AccountType;

import static com.banking.TestConstants.PRIMARY_ACCOUNT_INDEX;

@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {
    
	@Mock
    private AccountRepository accountRepository;
    
    private static final int ownerCustomerId = 345;
    private static final BigDecimal balance = new BigDecimal(200);
    
    @Test
    public void whenFindAllByOwnerCustomerId_thenReturnAccounts() {    	
    	
    	int ownerCustomerId = 345;
    	BigDecimal balance = new BigDecimal(200);
    	Account account = new Account(AccountType.PRIMARY, ownerCustomerId, balance);
    	List<Account> accounts = new ArrayList<>();
    	accounts.add(account);
    	
    	when(accountRepository.findAllByOwnerCustomerId(ownerCustomerId)).thenReturn(accounts);
    	
    	List<Account> foundList = accountRepository.findAllByOwnerCustomerId(ownerCustomerId);
    	
    	Account found = foundList.get(PRIMARY_ACCOUNT_INDEX); // only one element
    	
    	assertThat(found.getBalance()).isEqualTo(account.getBalance()); 	
    }
    
    @Test
    public void whenFindBalanceById_thenReturnBalance() {    	
    	
    	Account account = new Account(AccountType.PRIMARY, ownerCustomerId, balance);
    	
    	when(accountRepository.findBalanceById(account.getId())).thenReturn(balance);
    	
    	BigDecimal found = accountRepository.findBalanceById(account.getId());
    	
    	assertTrue(found.compareTo(balance) == 0);
    }
 
}