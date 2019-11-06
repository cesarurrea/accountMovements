package coop.tecso.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

		return service.create(dto);

	}

	@DeleteMapping
	public ResponseApplication<AccountDto> delete(@RequestParam String accountNumber) {
		return service.delete(accountNumber);
	}

	@GetMapping("/list")
	public ResponseApplication<AccountDto> findAll() {
		return service.findAll();
	}

}
