package com.banking.repository;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.banking.model.Transaction;

import static com.banking.TestConstants.PRIMARY_ACCOUNT_INDEX;

@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest {
 
    @Mock
    private TransactionRepository transactionRepository;
    
    @Test
    public void whenFindAllByAccountId_thenReturnTransactions() {    	
    	int accountId = 25;
    	Account account = new Account(accountId, AccountType.PRIMARY, 13, new BigDecimal(200));
    	Transaction transaction = new Transaction(account, account.getId(), new BigDecimal(100));
    	List<Transaction> transactions = new ArrayList<>();
    	transactions.add(transaction);
    	
    	when(transactionRepository.findAllByAccountId(accountId)).thenReturn(transactions);
    	
    	Transaction found = transactionRepository.findAllByAccountId(accountId).get(PRIMARY_ACCOUNT_INDEX); // only one element for testing purposes
    	
    	assertThat(found.getAmount()).isEqualTo(transaction.getAmount()); 	
    }
 
}