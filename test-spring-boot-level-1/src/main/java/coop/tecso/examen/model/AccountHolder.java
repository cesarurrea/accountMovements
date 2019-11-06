package coop.tecso.examen.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class AccountHolder extends AbstractPersistentObject {

	private static final long serialVersionUID = 1L;

	private String rut;
	private String cc;
	private String name;
	private String lastName;
	private String bussinesName;
	private Date establishmentDate;
	private String legalEntity;

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
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBussinesName() {
		return bussinesName;
	}

	public void setBussinesName(String bussinesName) {
		this.bussinesName = bussinesName;
	}

	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

}
