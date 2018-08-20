package com.banking.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	public List<Account> findAllByOwnerCustomerId(Integer id);
	public BigDecimal findBalanceById(Integer id);
}
