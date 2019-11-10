package coop.tecso.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountHolderDto;
import coop.tecso.examen.service.IAccountHolderService;
import coop.tecso.examen.service.ResponseApplication;

@RestController
@RequestMapping("/accountHolder")
public class AccountHolderController {

	@Autowired
	private IAccountHolderService service;

	@PostMapping
	public ResponseApplication<AccountHolderDto> create(@RequestBody AccountHolderDto dto) {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		response.setResponse(dto);

		try {

			response = service.create(dto);

		} catch (Exception e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		return response;

	}

	@PutMapping
	public ResponseApplication<AccountHolderDto> update(@RequestBody AccountHolderDto dto) {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		response.setResponse(dto);

		try {
			response = service.update(dto);
		} catch (Exception e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		return response;

	}

	@DeleteMapping
	public ResponseApplication<AccountHolderDto> delete(@RequestParam String rut) {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		try {
			response = service.delete(rut);
		} catch (Exception e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		return response;
	}

	@GetMapping
	public ResponseApplication<AccountHolderDto> findById(@RequestParam String rut) {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		try {
			response = service.findById(rut);
		} catch (Exception e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		return response;
	}

	@GetMapping("/list")
	public ResponseApplication<AccountHolderDto> findAll() {
		ResponseApplication<AccountHolderDto> response = new ResponseApplication<>();

		try {
			response = service.findAll();
		} catch (Exception e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		return response;
	}

}
