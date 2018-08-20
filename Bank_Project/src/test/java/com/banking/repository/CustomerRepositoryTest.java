package com.banking.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.banking.model.Customer;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerRepositoryTest {
 
    @Mock
    private CustomerRepository customerRepository;
    
    @Test
    public void whenFindById_thenReturnCustomer() {    	

    	int customerId = 123;
    	String name = "Mika";
    	String surname = "Hakkinen";
    	Customer customer = new Customer(customerId,name, surname);
    	Customer wrongCustomer = new Customer(customerId,"John", "Brown");
    	
    	Optional<Customer> optionalCustomer = Optional.of(customer);
    	
    	when(customerRepository.findById(customerId)).thenReturn(optionalCustomer);
    	Optional<Customer> found = customerRepository.findById(customerId);
  	
    	assertThat(found.orElse(wrongCustomer)).isEqualTo(customer); 	
    }
 
}