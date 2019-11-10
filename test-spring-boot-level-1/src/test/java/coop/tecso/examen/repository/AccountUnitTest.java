package coop.tecso.examen.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import coop.tecso.examen.common.enums.Currency;
import coop.tecso.examen.common.enums.MovementType;
import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.dto.AccountMovementDto;
import coop.tecso.examen.model.Account;
import coop.tecso.examen.service.IAccountMovementService;
import coop.tecso.examen.service.IAccountService;
import coop.tecso.examen.service.ResponseApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountUnitTest {

	@Autowired
	private IAccountService service;

	@Autowired
	private IAccountRepository dao;

	@Autowired
	private IAccountMovementService movementService;

	@Before
	public void setUp() {
		AccountDto dto = new AccountDto("0191111", Currency.PESO, new BigDecimal(1200.6543));

		service.create(dto);

		dto = new AccountDto("0192222", Currency.DOLAR, new BigDecimal(400.654));

		service.create(dto);

		dto = new AccountDto("019333", Currency.EURO, new BigDecimal(600.654));

		service.create(dto);

		dto = new AccountDto("019444", Currency.PESO, new BigDecimal(80.654));

		service.create(dto);

		// Account with movements
		AccountMovementDto movementDto = new AccountMovementDto("019333", new Date(), MovementType.CREDIT,
				"Mi descripcion", new BigDecimal(800.33));

		movementService.create(movementDto);

	}

	@Test
	public void validateDeleteAccount() {

		ResponseApplication<AccountDto> response = service.delete("019444");

		assertEquals(ResponseType.OK, response.getResponseType());

		Account account = dao.findByAccountNumber("019444");

		assertNull(account);

	}

	@Test
	public void validateNotDeleteAccountWithMovements() {

		ResponseApplication<AccountDto> response = service.delete("019333");

		assertEquals(ResponseType.ERROR, response.getResponseType());

		Account account = dao.findByAccountNumber("019444");

		assertNotNull(account);

	}

}
