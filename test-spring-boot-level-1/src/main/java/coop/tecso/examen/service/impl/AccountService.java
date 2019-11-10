package coop.tecso.examen.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.exception.AccountException;
import coop.tecso.examen.exception.DomainException;
import coop.tecso.examen.model.Account;
import coop.tecso.examen.repository.IAccountMovementRepository;
import coop.tecso.examen.repository.IAccountRepository;
import coop.tecso.examen.service.IAccountService;
import coop.tecso.examen.service.ResponseApplication;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private IAccountRepository dao;

	@Autowired
	private IAccountMovementRepository movementsDao;

	@Override
	public ResponseApplication<AccountDto> create(AccountDto dto) {

		ResponseApplication<AccountDto> response = new ResponseApplication<>();
		response.setResponse(dto);

		try {
			dto.isValid();
		} catch (DomainException e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		try {
			validatePossibleInsert(dto.getAccountNumber());
		} catch (DomainException e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		Account entity = Mapper.AccountMapper.map(dto);

		dao.save(entity);

		response.setMessage("Creacion Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;
	}

	@Override
	public ResponseApplication<AccountDto> delete(String accountNumber) {

		ResponseApplication<AccountDto> response = new ResponseApplication<>();

		try {
			Account entity = getAccount(accountNumber);

			validateDeleteAccountMovements(accountNumber);

			dao.delete(entity);

			response.setResponse(Mapper.AccountMapper.map(entity));

		} catch (DomainException e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		response.setMessage("Eliminacion Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;
	}

	@Override
	public ResponseApplication<AccountDto> findAll() {

		ResponseApplication<AccountDto> response = new ResponseApplication<>();

		List<Account> lista = dao.findAll();

		List<AccountDto> listaDto = lista.stream().map(p -> Mapper.AccountMapper.map(p)).collect(Collectors.toList());

		response.setList(listaDto);

		response.setMessage("Consulta Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;

	}

	public Account getAccount(String accountNumber) {

		Account entity = dao.findByAccountNumber(accountNumber);

		if (entity == null) {
			throw new AccountException("La cuenta no existe.");
		}

		return entity;

	}

	public boolean validatePossibleInsert(String accountNumber) {

		boolean exist = dao.existsAccount(accountNumber);

		if (exist) {
			throw new AccountException("Ya existe la cuenta.");
		}

		return true;

	}

	public boolean validateDeleteAccountMovements(String accountNumber) {

		if (movementsDao.existsAccountMovements(accountNumber)) {
			throw new AccountException("Las cuentas solo pueden eliminarse si no tienen movimientos asociados");
		}

		return true;

	}

}
