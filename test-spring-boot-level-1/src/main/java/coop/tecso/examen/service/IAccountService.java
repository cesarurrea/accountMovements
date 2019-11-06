package coop.tecso.examen.service;

import coop.tecso.examen.dto.AccountDto;

public interface IAccountService {

	ResponseApplication<AccountDto> create(AccountDto dto);

	ResponseApplication<AccountDto> delete(String account);

	ResponseApplication<AccountDto> findAll();

}
