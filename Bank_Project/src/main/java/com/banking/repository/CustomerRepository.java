package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
