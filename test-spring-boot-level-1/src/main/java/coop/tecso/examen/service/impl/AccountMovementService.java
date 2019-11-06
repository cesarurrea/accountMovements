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

		AccountMovement model = Mapper.AccountMovementMapper.map(dto);

		ResponseApplication<AccountMovementDto> response = new ResponseApplication<>();
		response.setResponse(dto);

		Account account = accountDao.findByAccountNumber(dto.getAccountNumber());

		if (account == null) {
			response.setMessage("La cuenta especificada no existe");
			response.setResponseType(ResponseType.ERROR);
			return response;

		}

		Currency currency = Currency.valueOf(account.getCurrency());

		switch (currency) {
		case PESO:
			if (dto.getAmmount().doubleValue() > Double.valueOf(1000) && dto.getMovementType() == MovementType.DEBIT) {
				response.setMessage("El debito no puede ser superior a 1000 pesos");
				response.setResponseType(ResponseType.ERROR);
				return response;

			}

		case DOLAR:
			if (dto.getAmmount().doubleValue() > Double.valueOf(300) && dto.getMovementType() == MovementType.DEBIT) {
				response.setMessage("El debito no puede ser superior a 300 dolares");
				response.setResponseType(ResponseType.ERROR);
				return response;

			}

		case EURO:
			if (dto.getAmmount().doubleValue() > Double.valueOf(150) && dto.getMovementType() == MovementType.DEBIT) {
				response.setMessage("El debito no puede ser superior a 150 euros");
				response.setResponseType(ResponseType.ERROR);
				return response;

			}

		}

		Double balance = (dto.getMovementType() == MovementType.DEBIT
				? account.getBalance().doubleValue() - dto.getAmmount().doubleValue()
				: account.getBalance().doubleValue() + dto.getAmmount().doubleValue());

		if (balance < 0) {
			response.setMessage("No hay fondos suficientes para realizar el debito");
			response.setResponseType(ResponseType.ERROR);
			return response;
		}

		account.setBalance(BigDecimal.valueOf(balance));

		accountDao.save(account);
		dao.save(model);

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

}
