package coop.tecso.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.service.IAccountService;
import coop.tecso.examen.service.ResponseApplication;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private IAccountService service;

	@PostMapping
	public ResponseApplication<AccountDto> create(@RequestBody AccountDto dto) {

		ResponseApplication<AccountDto> response = new ResponseApplication<>();

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

	@DeleteMapping
	public ResponseApplication<AccountDto> delete(@RequestParam String accountNumber) {
		ResponseApplication<AccountDto> response = new ResponseApplication<>();

		try {
			response = service.delete(accountNumber);
		} catch (Exception e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		return response;

	}

	@GetMapping("/list")
	public ResponseApplication<AccountDto> findAll() {
		ResponseApplication<AccountDto> response = new ResponseApplication<>();

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
