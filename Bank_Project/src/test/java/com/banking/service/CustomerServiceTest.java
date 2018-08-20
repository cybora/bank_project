package com.banking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;

import com.banking.model.Account;
import com.banking.model.AccountType;
import com.banking.model.Customer;
import com.banking.model.CustomerDetail;
import com.banking.model.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;

import static com.banking.TestConstants.PRIMARY_ACCOUNT_INDEX;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
 
    @MockBean
    private CustomerRepository customerRepositoryMock;
    
    @Mock
    private CustomerService customerServiceMock;
    
	
	public static final BigDecimal accountBalance = new BigDecimal(200.00);
	public static final int customerId = 25;
	public static final BigDecimal amount = new BigDecimal(100);	
	public static final String name = "Hans";
	public static final String surname = "Zimmer";
    
    @Test
    public void whenGetCustomerDetail_thenReturnCustomerDetail() {
    	Account account = new Account(AccountType.PRIMARY, customerId, accountBalance);
    	Transaction transaction = new Transaction(account, account.getId(), amount);
    	List<Transaction> transactionList = new ArrayList<>();
    	transactionList.add(transaction);
    	
    	CustomerDetail customerDetail = new CustomerDetail(name, surname, accountBalance, transactionList);
    	
    	Mockito.when(customerServiceMock.getCustomerDetail(customerId)).thenReturn(customerDetail);    	
    	
    	CustomerDetail foundCustomerDetail = customerServiceMock.getCustomerDetail(customerId);
    	     
         assertThat(foundCustomerDetail.getName())
          .isEqualTo(name);
     }
    
    @Test
    public void whenGetCustomer_thenReturnCustomer() {
    	
    	Customer customer = new Customer(customerId, name, surname);
    	
    	Mockito.when(customerServiceMock.getCustomer(customerId)).thenReturn(customer); 
    	
    	Customer found = customerServiceMock.getCustomer(customerId);   	
      
         assertThat(found.getName())
          .isEqualTo(name);
     }
    
    @Test
    public void whenGetCustomerBalance_thenReturnCustomerBalance() {
    	
    	Customer customer = new Customer(customerId, name, surname);
		Account account = new Account(AccountType.PRIMARY, customerId, accountBalance);
    	
    	customerRepository.save(customer);
    	accountRepository.save(account);
    	
    	BigDecimal found = customerService.getCustomerBalance(customerId);   	
         
         assertTrue(found.compareTo(account.getBalance()) == 0);
     }
    
    @Test
    public void whenGetCustomerTransactions_thenReturnCustomerTransactions() {
    	
    	Customer customer = new Customer(customerId, name, surname);
		Account account = new Account(AccountType.PRIMARY, customerId, accountBalance);
		Transaction transaction = new Transaction(account, account.getId(), amount);
    	
    	customerRepository.save(customer);
    	accountRepository.save(account);
    	transactionRepository.save(transaction);
    	
    	List<Transaction> foundTransactions = customerService.getCustomerTransactions(customerId);
    	
    	Transaction testTransaction = foundTransactions.get(PRIMARY_ACCOUNT_INDEX);
         
         assertTrue(testTransaction.getAmount().compareTo(amount) == 0);
     }
    
}
