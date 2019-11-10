package coop.tecso.examen.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import coop.tecso.examen.model.AccountMovement;
import coop.tecso.examen.service.IAccountMovementService;
import coop.tecso.examen.service.IAccountService;
import coop.tecso.examen.service.ResponseApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountMovementUnitTest {

	@Autowired
	private IAccountMovementService service;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IAccountRepository accountDao;

	@Before
	public void setUp() {
		AccountDto dto = new AccountDto("0191111", Currency.PESO, new BigDecimal(100.333));

		accountService.create(dto);

		dto = new AccountDto("0192222", Currency.PESO, new BigDecimal(100.333));

		accountService.create(dto);

		dto = new AccountDto("0193333", Currency.EURO, new BigDecimal(50));

		accountService.create(dto);

	}

	@Test
	public void validateAccountDebitDiscountMovement() {
		AccountMovementDto dto = new AccountMovementDto("0191111", new Date(), MovementType.DEBIT, "Mi Descripcion",
				new BigDecimal(50));

		ResponseApplication<AccountMovementDto> response = service.create(dto);

		assertEquals(ResponseType.OK, response.getResponseType());

		Account account = accountDao.findByAccountNumber("0191111");

		assertNotNull(account);

		assertEquals(new BigDecimal(50.33).setScale(2, BigDecimal.ROUND_UP), account.getBalance());

	}

	@Test
	public void validateAccountCreditDiscountMovement() {
		AccountMovementDto dto = new AccountMovementDto("0192222", new Date(), MovementType.CREDIT, "Mi Descripcion",
				new BigDecimal(50));

		ResponseApplication<AccountMovementDto> response = service.create(dto);

		assertEquals(ResponseType.OK, response.getResponseType());

		Account account = accountDao.findByAccountNumber("0192222");

		assertNotNull(account);

		assertEquals(new BigDecimal(150.33).setScale(2, BigDecimal.ROUND_FLOOR), account.getBalance());

	}

	@Test
	public void validateDescubierto() {
		AccountMovementDto dto = new AccountMovementDto("0193333", new Date(), MovementType.DEBIT, "Mi Descripcion",
				new BigDecimal(500));

		ResponseApplication<AccountMovementDto> response = service.create(dto);

		assertEquals(ResponseType.ERROR, response.getResponseType());
		assertEquals("[AccountMovementException] Ha excedido el valor descubierto, transaccion Rechazada",
				response.getMessage());

		// Validamos que la cuenta se conserve sin debito
		Account account = accountDao.findByAccountNumber("0193333");

		assertNotNull(account);

		assertEquals(new BigDecimal(50).setScale(2, BigDecimal.ROUND_FLOOR), account.getBalance());

	}

	@Test
	public void validateCreditMovement() {

	}

}
