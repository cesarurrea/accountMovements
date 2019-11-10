package coop.tecso.examen.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import coop.tecso.examen.common.enums.LegalEntity;
import coop.tecso.examen.common.enums.ResponseType;
import coop.tecso.examen.dto.AccountHolderDto;
import coop.tecso.examen.service.ResponseApplication;
import coop.tecso.examen.service.impl.AccountHolderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountHolderUnitTest {

	@Autowired
	private AccountHolderService service;

	@Before
	public void setUp() {
		AccountHolderDto dto = new AccountHolderDto("98005555", null, null, null, "TEST S.A", new Date(),
				LegalEntity.JURIDICAL);

		service.create(dto);

		dto = new AccountHolderDto("98008888", null, null, null, "TEST2 S.A", new Date(), LegalEntity.JURIDICAL);

		service.create(dto);

	}

	@Test
	public void validateExistsAccountHolder() {

		ResponseApplication<AccountHolderDto> response = service.findById("98005555");

		assertNotNull(response.getResponse());
		assertEquals("98005555", response.getResponse().getRut());

	}

	@Test
	public void validateDeleteAccountHolder() {
		
		//Delete record
		ResponseApplication<AccountHolderDto> response = service.delete("98008888");
		
		//Check deletion operation results
		assertEquals(ResponseType.OK, response.getResponseType());
		
		//Check exists record
		response = service.findById("98008888");
		
		//Check results for finding record.
		assertEquals("No existe un Titular asociado al RUT suministrado.", response.getMessage());

	}

	@Test
	public void validateUpdateAccountHolder() {
		AccountHolderDto dto = new AccountHolderDto("98005555", null, null, null, "ACTUALIZADO S.A", new Date(),
				LegalEntity.JURIDICAL);

		ResponseApplication<AccountHolderDto> response = service.update(dto);

		assertEquals(ResponseType.OK, response.getResponseType());

		response = service.findById("98005555");

		assertNotNull(response.getResponse());
		assertEquals(ResponseType.OK, response.getResponseType());

		assertEquals("ACTUALIZADO S.A", response.getResponse().getBussinesName());

	}

}
