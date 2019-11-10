package coop.tecso.examen.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coop.tecso.examen.common.enums.Currency;
import coop.tecso.examen.exception.AccountValidationException;

public class AccountDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNumber;
	private Currency currency;
	private BigDecimal balance;

	public AccountDto() {
		super();
	}

	public AccountDto(String accountNumber, Currency currency, BigDecimal balance) {
		super();
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.balance = balance;
	}

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

	@JsonIgnore
	public boolean isValid() {
		String attribute = (this.getAccountNumber() == null ? "" : this.getAccountNumber().trim());

		if (attribute.length() == 0) {
			throw new AccountValidationException("El NUMERO DE CUENTA es obligatorio.");
		}

		if (this.getCurrency() == null) {
			throw new AccountValidationException("La MONEDA es obligatoria.");
		}

		if (this.getBalance() == null) {
			throw new AccountValidationException("El valor SALDO es obligatorio.");
		}

		return true;

	}

}
