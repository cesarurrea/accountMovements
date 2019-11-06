package coop.tecso.examen.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountDto;
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

		if (validateExistsAccount(dto.getAccountNumber())) {
			response.setMessage("Ya existe el numero de Cuenta");
			response.setResponseType(ResponseType.ERROR);
			return response;
		}

		Account entity = Mapper.AccountMapper.map(dto);

		dao.save(entity);

		response.setMessage("Creacion Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;
	}

	@Override
	public ResponseApplication<AccountDto> delete(String account) {

		ResponseApplication<AccountDto> response = new ResponseApplication<>();

		Account entity = findByAccountNumber(account);

		if (entity == null) {
			response.setMessage("La cuenta especificada para eliminacion no existe");
			response.setResponseType(ResponseType.ERROR);
			return response;
		}

		if (movementsDao.existsAccountMovements(account)) {
			response.setMessage("Las cuentas solo pueden eliminarse si no tienen movimientos asociados");
			response.setResponseType(ResponseType.ERROR);
			return response;

		}

		dao.delete(entity);

		response.setResponse(Mapper.AccountMapper.map(entity));

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

	public Boolean validateExistsAccount(String accountNumber) {

		return dao.existsAccount(accountNumber);

	}

	public Account findByAccountNumber(String accountNumber) {
		return dao.findByAccountNumber(accountNumber);
	}

}
