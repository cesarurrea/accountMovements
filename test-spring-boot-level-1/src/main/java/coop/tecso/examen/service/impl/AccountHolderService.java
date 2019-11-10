package coop.tecso.examen.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountHolderDto;
import coop.tecso.examen.exception.AccountHolderException;
import coop.tecso.examen.exception.DomainException;
import coop.tecso.examen.model.AccountHolder;
import coop.tecso.examen.repository.IAccountHolderRepository;
import coop.tecso.examen.service.IAccountHolderService;
import coop.tecso.examen.service.ResponseApplication;

@Service
public class AccountHolderService implements IAccountHolderService {

	@Autowired
	private IAccountHolderRepository dao;

	@Override
	public ResponseApplication<AccountHolderDto> create(AccountHolderDto dto) {

		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();
		response.setResponse(dto);

		try {
			dto.isValid();
		} catch (DomainException e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		AccountHolder entity = Mapper.AccountHolderMapper.map(dto);

		try {
			validatePossibleInsert(dto.getRut());
		} catch (DomainException e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		dao.save(entity);

		response.setMessage("Creacion Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;

	}

	@Override
	public ResponseApplication<AccountHolderDto> update(AccountHolderDto dto) {

		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();
		response.setResponse(dto);

		try {
			dto.isValid();
		} catch (DomainException e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		try {
			AccountHolder entityPlusId = getAccountHolder(dto.getRut());
			entityPlusId.setRut(dto.getRut());
			entityPlusId.setCc(dto.getCc());
			entityPlusId.setName(dto.getName());
			entityPlusId.setLastName(dto.getLastName());
			entityPlusId.setBussinesName(dto.getBussinesName());
			entityPlusId.setEstablishmentDate(dto.getEstablishmentDate());
			entityPlusId.setLegalEntity(dto.getLegalEntity().name());

			dao.save(entityPlusId);

		} catch (DomainException e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		response.setMessage("Actualizacion Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;
	}

	@Override
	public ResponseApplication<AccountHolderDto> delete(String rut) {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		try {
			AccountHolder entity = getAccountHolder(rut);

			dao.delete(entity);

			response.setResponse(Mapper.AccountHolderMapper.map(entity));

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
	public ResponseApplication<AccountHolderDto> findById(String rut) {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		try {
			AccountHolder entityPlusId = getAccountHolder(rut);
			response.setResponse(Mapper.AccountHolderMapper.map(entityPlusId));

		} catch (DomainException e) {
			response.setResponseType(ResponseType.OK);
			response.setMessage(e.getMessage());

			return response;
		}

		response.setMessage("Consulta Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;
	}

	@Override
	public ResponseApplication<AccountHolderDto> findAll() {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		List<AccountHolder> lista = dao.findAll();

		List<AccountHolderDto> listaDto = lista.stream().map(p -> Mapper.AccountHolderMapper.map(p))
				.collect(Collectors.toList());

		response.setList(listaDto);

		response.setMessage("Consulta Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;

	}

	public boolean validatePossibleInsert(String rut) {

		boolean exist = dao.existsRut(rut);

		if (exist) {
			throw new AccountHolderException("Ya existe un titular con el mismo rut.");
		}

		return true;

	}

	public AccountHolder getAccountHolder(String rut) {

		AccountHolder entity = dao.findByRut(rut);

		if (entity == null) {
			throw new AccountHolderException("No existe un Titular asociado al RUT suministrado.");
		}

		return entity;

	}

}
