package coop.tecso.examen.service;

import coop.tecso.examen.dto.AccountHolderDto;

public interface IAccountHolderService {

	ResponseApplication<AccountHolderDto> create(AccountHolderDto dto);

	ResponseApplication<AccountHolderDto> update(AccountHolderDto dto);

	ResponseApplication<AccountHolderDto> delete(String rut);

	ResponseApplication<AccountHolderDto> findById(String rut);

	ResponseApplication<AccountHolderDto> findAll();

}
