package coop.tecso.examen.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import coop.tecso.examen.common.enums.MovementType;

public class AccountMovementDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNumber;
	private Date movementDate;
	private MovementType movementType;
	private String description;
	private BigDecimal ammount;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}

}
