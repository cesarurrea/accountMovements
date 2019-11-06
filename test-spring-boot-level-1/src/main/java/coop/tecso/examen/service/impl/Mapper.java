package coop.tecso.examen.service.impl;

import java.util.Date;

import coop.tecso.examen.common.enums.Currency;
import coop.tecso.examen.common.enums.LegalEntity;
import coop.tecso.examen.common.enums.MovementType;
import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.dto.AccountHolderDto;
import coop.tecso.examen.dto.AccountMovementDto;
import coop.tecso.examen.model.Account;
import coop.tecso.examen.model.AccountHolder;
import coop.tecso.examen.model.AccountMovement;

public class Mapper {

	public static class AccountHolderMapper {

		public static AccountHolder map(AccountHolderDto dto) {
			AccountHolder model = new AccountHolder();
			model.setRut(dto.getRut());
			model.setCc(dto.getCc());
			model.setName(dto.getName());
			model.setLastName(dto.getLastName());
			model.setBussinesName(dto.getBussinesName());
			model.setEstablishmentDate(dto.getEstablishmentDate());
			model.setLegalEntity(dto.getLegalEntity().name());

			return model;

		}

		public static AccountHolderDto map(AccountHolder model) {
			AccountHolderDto dto = new AccountHolderDto();

			dto.setRut(model.getRut());
			dto.setCc(model.getCc());
			dto.setName(model.getName());
			dto.setLastName(model.getLastName());
			dto.setBussinesName(model.getBussinesName());
			dto.setEstablishmentDate(model.getEstablishmentDate());
			dto.setLegalEntity(LegalEntity.valueOf(model.getLegalEntity()));

			return dto;

		}

	}

	public static class AccountMapper {
		public static Account map(AccountDto dto) {
			Account model = new Account();
			model.setAccountNumber(dto.getAccountNumber());
			model.setBalance(dto.getBalance());
			model.setCurrency(dto.getCurrency().name());

			return model;

		}

		public static AccountDto map(Account model) {
			AccountDto dto = new AccountDto();
			dto.setAccountNumber(model.getAccountNumber());
			dto.setBalance(model.getBalance());
			dto.setCurrency(Currency.valueOf(model.getCurrency()));

			return dto;

		}

	}

	public static class AccountMovementMapper {
		public static AccountMovement map(AccountMovementDto dto) {
			AccountMovement model = new AccountMovement();
			model.setAccountNumber(dto.getAccountNumber());
			model.setDescription(dto.getDescription());
			model.setMovementDate(new Date());
			model.setMovementType(dto.getMovementType().name());
			model.setAmmount(dto.getAmmount());

			return model;
		}

		public static AccountMovementDto map(AccountMovement model) {
			AccountMovementDto dto = new AccountMovementDto();
			dto.setAccountNumber(model.getAccountNumber());
			dto.setDescription(model.getDescription());
			dto.setMovementDate(model.getMovementDate());
			dto.setMovementType(MovementType.valueOf(model.getMovementType()));
			dto.setAmmount(model.getAmmount());

			return dto;
		}

	}

}
