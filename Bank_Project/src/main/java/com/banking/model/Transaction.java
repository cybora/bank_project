package com.banking.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Transaction")
public class Transaction {
	
	public Transaction(Account account, int otherPartyId, BigDecimal amount) {
		this.otherPartyId = otherPartyId;
		this.amount = amount;
		this.account = account;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	@Column(name = "other_party_id")
	private int otherPartyId;
	
	private BigDecimal amount;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne
    @JoinColumn(name = "account_id")
	@JsonIgnore
    private Account account;
	
	@PrePersist
	protected void onCreate() {
		date = new Date();
	 }
	
}
