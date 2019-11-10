package coop.tecso.examen.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coop.tecso.examen.common.enums.Currency;
import coop.tecso.examen.common.enums.MovementType;
import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountMovementDto;
import coop.tecso.examen.exception.AccountMovementException;
import coop.tecso.examen.exception.DomainException;
import coop.tecso.examen.model.Account;
import coop.tecso.examen.model.AccountMovement;
import coop.tecso.examen.repository.IAccountMovementRepository;
import coop.tecso.examen.repository.IAccountRepository;
import coop.tecso.examen.service.IAccountMovementService;
import coop.tecso.examen.service.ResponseApplication;

@Service
public class AccountMovementService implements IAccountMovementService {

	@Autowired
	private IAccountMovementRepository dao;

	@Autowired
	private IAccountRepository accountDao;

	@Override
	public ResponseApplication<AccountMovementDto> create(AccountMovementDto dto) {		

		ResponseApplication<AccountMovementDto> response = new ResponseApplication<>();
		response.setResponse(dto);

		try {
			dto.isValid();
		} catch (DomainException e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}
		
		AccountMovement model = Mapper.AccountMovementMapper.map(dto);

		try {

			Account account = getAccount(dto.getAccountNumber());

			validateAmmount(account, dto);

			Double balance = (dto.getMovementType() == MovementType.DEBIT
					? account.getBalance().doubleValue() - dto.getAmmount().doubleValue()
					: account.getBalance().doubleValue() + dto.getAmmount().doubleValue());

			account.setBalance(BigDecimal.valueOf(balance));

			accountDao.save(account);

			dao.save(model);

		} catch (DomainException e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		response.setMessage("Creacion Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;
	}

	@Override
	public ResponseApplication<AccountMovementDto> movementsPerAccount(String account) {
		ResponseApplication<AccountMovementDto> response = new ResponseApplication<>();

		List<AccountMovement> lista = dao.movementsPerAccount(account);

		List<AccountMovementDto> listaDto = lista.stream().map(p -> Mapper.AccountMovementMapper.map(p))
				.collect(Collectors.toList());

		response.setList(listaDto);

		response.setMessage("Consulta Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;
	}

	public Account getAccount(String accountNumber) {

		Account entity = accountDao.findByAccountNumber(accountNumber);

		if (entity == null) {
			throw new AccountMovementException("La cuenta no existe.");
		}

		return entity;

	}

	public boolean validateAmmount(Account account, AccountMovementDto dto) {

		if (dto.getMovementType() == MovementType.DEBIT) {
			switch (Currency.valueOf(account.getCurrency())) {
			case PESO:
				if (account.getBalance().doubleValue() - dto.getAmmount().doubleValue() < -1000) {
					throw new AccountMovementException("Ha excedido el valor descubierto, transaccion Rechazada");
				}
				break;
			case DOLAR:
				if (account.getBalance().doubleValue() - dto.getAmmount().doubleValue() < -300) {
					throw new AccountMovementException("Ha excedido el valor descubierto, transaccion Rechazada");
				}
				break;
			case EURO:
				if (account.getBalance().doubleValue() - dto.getAmmount().doubleValue() < -150) {
					throw new AccountMovementException("Ha excedido el valor descubierto, transaccion Rechazada");
				}
				break;

			default:
				throw new AccountMovementException(
						"Error validando Monto para el tipo de moneda, transaccion Rechazada");

			}

		}

		return true;

	}

}
