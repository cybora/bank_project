package com.banking.rest;

import java.math.BigDecimal;

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
import com.banking.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountRestTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
	private AccountService accountService;

    @Test
    public void shouldReturnCreatedSecondaryAccount() throws Exception {
    	
    	int ownerCustomerId = 22;
    	BigDecimal balance = new BigDecimal(30);
	
    	Account mockAccount = new Account(AccountType.SECONDARY, ownerCustomerId, balance);
    	
    	Mockito.when( 
    				accountService.createSecondaryAccount(Mockito.anyInt(),
						Mockito.any())).thenReturn(mockAccount);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/createSecondaryAccount/2/30").accept(
				MediaType.APPLICATION_JSON);
    	
    	MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    	
    	String expected = "{\"id\":0,\"accountType\":\"SECONDARY\",\"ownerCustomerId\":22,\"balance\":30}";
    	
    	JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
}