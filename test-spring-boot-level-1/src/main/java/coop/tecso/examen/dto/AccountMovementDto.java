package coop.tecso.examen.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coop.tecso.examen.common.enums.MovementType;
import coop.tecso.examen.exception.AccountMovementValidationException;

public class AccountMovementDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNumber;
	private Date movementDate;
	private MovementType movementType;
	private String description;
	private BigDecimal ammount;

	public AccountMovementDto() {
		super();
	}

	public AccountMovementDto(String accountNumber, Date movementDate, MovementType movementType, String description,
			BigDecimal ammount) {
		super();
		this.accountNumber = accountNumber;
		this.movementDate = movementDate;
		this.movementType = movementType;
		this.description = description;
		this.ammount = ammount;
	}

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
		this.description = (description != null
				? description.substring(0, (description.length() < 200 ? description.length() : 200))
				: description);

	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public void setAmmount(BigDecimal ammount) {

		ammount.setScale(2, BigDecimal.ROUND_FLOOR);

		this.ammount = ammount;
	}

	@JsonIgnore
	public boolean isValid() {
		String attribute = (this.getAccountNumber() == null ? "" : this.getAccountNumber().trim());

		if (attribute.length() == 0) {
			throw new AccountMovementValidationException("El NUMERO DE CUENTA es obligatorio.");
		}

		if (this.getMovementDate() == null) {
			throw new AccountMovementValidationException("La FECHA es obligatoria.");
		}

		if (this.getMovementType() == null) {
			throw new AccountMovementValidationException("El TIPO DE MOVIMIENTO es obligatorio.");
		}

		if (this.getDescription() == null) {
			throw new AccountMovementValidationException("La DESCRIIPCION es obligatoria");
		}

		if (this.getAmmount() == null) {
			throw new AccountMovementValidationException("El IMPORTE es obligatorio.");
		}

		return true;

	}

}
