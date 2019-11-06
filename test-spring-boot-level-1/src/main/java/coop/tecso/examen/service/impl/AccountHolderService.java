package coop.tecso.examen.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountHolderDto;
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

		AccountHolder entity = Mapper.AccountHolderMapper.map(dto);

		if (validateExistsRut(dto.getRut())) {
			response.setMessage("Ya existe un titular con el RUT");
			response.setResponseType(ResponseType.ERROR);
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

		AccountHolder entityPlusId = dao.findByRut(dto.getRut());

		if (entityPlusId == null) {
			response.setMessage("Este Titular-Rut no existe en bd para actualización - Aunque podría hacer un UPSERT");
			response.setResponseType(ResponseType.ERROR);
			return response;
		}

		AccountHolder entity = Mapper.AccountHolderMapper.map(dto);

		entity.setId(entityPlusId.getId());

		if (validatePossibleChange(entity.getId(), entity.getRut())) {
			response.setMessage("Ya existe un registro con el RUT");
			response.setResponseType(ResponseType.ERROR);
			return response;

		}

		dao.save(entity);

		response.setMessage("Actualizacion Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;
	}

	@Override
	public ResponseApplication<AccountHolderDto> delete(String rut) {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		AccountHolder entityPlusId = dao.findByRut(rut);

		if (entityPlusId == null) {
			response.setMessage("El registro a eliminar no existe");
			response.setResponseType(ResponseType.ERROR);
			return response;
		}

		response.setResponse(Mapper.AccountHolderMapper.map(entityPlusId));

		dao.delete(entityPlusId);

		response.setMessage("Eliminacion Exitosa");
		response.setResponseType(ResponseType.OK);

		return response;

	}

	@Override
	public ResponseApplication<AccountHolderDto> findById(String rut) {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		AccountHolder entityPlusId = dao.findByRut(rut);

		if (entityPlusId == null) {
			response.setMessage("El registro no existe");
			response.setResponseType(ResponseType.ERROR);
			return response;
		}

		response.setResponse(Mapper.AccountHolderMapper.map(entityPlusId));

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

	public Boolean validateExistsRut(String rut) {

		return dao.existsRut(rut);

	}

	public Boolean validatePossibleChange(Long id, String rut) {

		return dao.possibleChange(id, rut);

	}

}
