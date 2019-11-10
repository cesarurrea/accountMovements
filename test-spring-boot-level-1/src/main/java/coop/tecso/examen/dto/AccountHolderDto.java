package coop.tecso.examen.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coop.tecso.examen.common.enums.LegalEntity;
import coop.tecso.examen.exception.AccountHolderValidationException;

public class AccountHolderDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rut;
	private String cc;
	private String name;
	private String lastName;
	private String bussinesName;
	private Date establishmentDate;
	private LegalEntity legalEntity;

	public AccountHolderDto() {
		super();
	}

	public AccountHolderDto(String rut, String cc, String name, String lastName, String bussinesName,
			Date establishmentDate, LegalEntity legalEntity) {
		super();
		this.rut = rut;
		this.cc = cc;
		this.name = name;
		this.lastName = lastName;
		this.bussinesName = bussinesName;
		this.establishmentDate = establishmentDate;
		this.legalEntity = legalEntity;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = (name != null ? name.substring(0, (name.length() < 80 ? name.length() : 80)) : name);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = (lastName != null ? lastName.substring(0, (lastName.length() < 250 ? lastName.length() : 250))
				: lastName);
	}

	public String getBussinesName() {
		return bussinesName;
	}

	public void setBussinesName(String bussinesName) {
		this.bussinesName = (bussinesName != null
				? bussinesName.substring(0, (bussinesName.length() < 100 ? bussinesName.length() : 100))
				: bussinesName);
	}

	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public LegalEntity getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(LegalEntity legalEntity) {
		this.legalEntity = legalEntity;
	}

	@JsonIgnore
	public boolean isValid() {
		String attribute = (this.getRut() == null ? "" : this.getRut().trim());

		if (attribute.length() == 0) {
			throw new AccountHolderValidationException("El RUT es obligatorio.");
		}

		if (this.getLegalEntity() == null) {
			throw new AccountHolderValidationException("El Tipo de Persona es obligatorio.");
		}

		switch (this.getLegalEntity()) {
		case NATURAL:
			attribute = (this.getName() == null ? "" : this.getName().trim());

			if (attribute.length() == 0) {
				throw new AccountHolderValidationException("El NOMBRE es obligatorio.");
			}

			attribute = (this.getLastName() == null ? "" : this.getLastName().trim());

			if (attribute.length() == 0) {
				throw new AccountHolderValidationException("El APELLIDO es obligatorio.");
			}

			attribute = (this.getCc() == null ? "" : this.getCc().trim());

			if (attribute.length() == 0) {
				throw new AccountHolderValidationException("La CEDULA es obligatoria.");
			}

			this.setBussinesName(null);
			this.setEstablishmentDate(null);

			break;
		case JURIDICAL:
			attribute = (this.getBussinesName() == null ? "" : this.getBussinesName().trim());

			if (attribute.length() == 0) {
				throw new AccountHolderValidationException("La RAZON SOCIAL es obligatoria.");
			}

			if (this.getEstablishmentDate() == null) {
				throw new AccountHolderValidationException("El año de fundacion es obligatorio.");
			}

			this.setName(null);
			this.setLastName(null);
			this.setCc(null);

			break;
		default:
			throw new AccountHolderValidationException("Tipo de Persona Desconocido");

		}

		return true;
	}

}
