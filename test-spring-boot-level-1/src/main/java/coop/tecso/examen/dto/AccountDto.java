package coop.tecso.examen.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import coop.tecso.examen.common.enums.Currency;

public class AccountDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNumber;
	private Currency currency;
	private BigDecimal balance;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {

		balance.setScale(2, BigDecimal.ROUND_FLOOR);

		this.balance = balance;
	}

}
