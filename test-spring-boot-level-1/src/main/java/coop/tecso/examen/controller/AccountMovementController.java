package coop.tecso.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountMovementDto;
import coop.tecso.examen.service.IAccountMovementService;
import coop.tecso.examen.service.ResponseApplication;

@RestController
@RequestMapping("/accountMovement")
public class AccountMovementController {

	@Autowired
	private IAccountMovementService service;

	@PostMapping
	public ResponseApplication<AccountMovementDto> create(@RequestBody AccountMovementDto dto) {

		ResponseApplication<AccountMovementDto> response = new ResponseApplication<>();

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

	@GetMapping("/movement/account")
	public ResponseApplication<AccountMovementDto> movementsPerAccount(@RequestParam String accountNumber) {
		ResponseApplication<AccountMovementDto> response = new ResponseApplication<>();

		try {
			response = service.movementsPerAccount(accountNumber);
		} catch (Exception e) {
			response.setResponseType(ResponseType.ERROR);
			response.setMessage("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

			return response;
		}

		return response;
	}

}
