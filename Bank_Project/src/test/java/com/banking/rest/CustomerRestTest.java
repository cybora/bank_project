package com.banking.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.skyscreamer.jsonassert.JSONAssert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.banking.model.Account;
import com.banking.model.AccountType;
import com.banking.model.CustomerDetail;
import com.banking.model.Transaction;
import com.banking.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerRestTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
	private CustomerService customerService;

    @Test
    public void shouldReturnCustomerDetail() throws Exception {
    	
    	int ownerCustomerId = 22;
    	BigDecimal balance = new BigDecimal(30);
    	BigDecimal amount = new BigDecimal(10);
    	String name = "Albert";
    	String surname = "Einstein";
    	
    	Account primaryAccount = new Account(AccountType.PRIMARY, ownerCustomerId, balance);
    	Account secondaryAccount = new Account(AccountType.SECONDARY, ownerCustomerId, balance);
    	Transaction transaction = new Transaction(primaryAccount, secondaryAccount.getId(), amount);
    	List<Transaction> transactions = new ArrayList<>();
    	transactions.add(transaction);
    	
    	CustomerDetail mockCustomerDetail =
    			new CustomerDetail(name, surname, balance, transactions);
    	
    	Mockito.when( 
    			customerService.getCustomerDetail(Mockito.anyInt())).thenReturn(mockCustomerDetail);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/customerDetails/3").accept(
				MediaType.APPLICATION_JSON);
    	
    	MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    	
    	String expected = "{\"name\":\"Albert\",\"surname\":\"Einstein\",\"balance\":30,\"transactions\":[{\"id\":0,\"otherPartyId\":0,\"amount\":10}]}";
    	
    	JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
}