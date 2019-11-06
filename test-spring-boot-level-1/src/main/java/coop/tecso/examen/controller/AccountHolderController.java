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

		return service.create(dto);

	}

	@PutMapping
	public ResponseApplication<AccountHolderDto> update(@RequestBody AccountHolderDto dto) {

		return service.update(dto);

	}

	@DeleteMapping
	public ResponseApplication<AccountHolderDto> delete(@RequestParam String rut) {
		return service.delete(rut);
	}

	@GetMapping
	public ResponseApplication<AccountHolderDto> findById(@RequestParam String rut) {
		return service.findById(rut);
	}
	
	@GetMapping("/list")
	public ResponseApplication<AccountHolderDto> findAll() {
		return service.findAll();
	}

}
