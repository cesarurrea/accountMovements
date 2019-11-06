package coop.tecso.examen.service;

import coop.tecso.examen.dto.AccountMovementDto;

public interface IAccountMovementService {

	ResponseApplication<AccountMovementDto> create(AccountMovementDto dto);

	ResponseApplication<AccountMovementDto> movementsPerAccount(String account);

}
