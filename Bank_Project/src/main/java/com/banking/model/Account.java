package com.banking.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Account")
public class Account {

	public Account(AccountType accountType, int ownerCustomerId, BigDecimal balance) {
		super();
		this.accountType = accountType;
		this.ownerCustomerId = ownerCustomerId;
		this.balance = balance;
	}
	
	public Account(int id, AccountType accountType, int ownerCustomerId, BigDecimal balance) {
		super();
		this.id = id;
		this.accountType = accountType;
		this.ownerCustomerId = ownerCustomerId;
		this.balance = balance;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	@Column(name = "account_type")
	@Enumerated(EnumType.ORDINAL)
	private AccountType accountType;
	
	@Column(name = "owner_customer_id")
	private int ownerCustomerId;
	
	private BigDecimal balance;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Transaction> transactionList;
	
}
