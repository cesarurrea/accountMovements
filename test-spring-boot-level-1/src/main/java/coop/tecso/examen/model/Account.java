package coop.tecso.examen.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class Account extends AbstractPersistentObject {

	private static final long serialVersionUID = 1L;

	private String accountNumber;
	private String currency;
	private BigDecimal balance;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
