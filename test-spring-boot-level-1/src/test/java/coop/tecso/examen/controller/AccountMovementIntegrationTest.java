package coop.tecso.examen.controller;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import coop.tecso.examen.MainApplication;
import coop.tecso.examen.common.enums.Currency;
import coop.tecso.examen.common.enums.MovementType;
import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.dto.AccountMovementDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountMovementIntegrationTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Before
	public void setUp() {
		AccountDto dto = new AccountDto("018111", Currency.EURO, new BigDecimal(1300.4556));

		HttpEntity<AccountDto> requestObject = new HttpEntity<AccountDto>(dto, headers);

		restTemplate.exchange(createURLWithPort("/account"), HttpMethod.POST, requestObject, String.class);

	}

	@Test
	public void validateDataIsCompleteNewAccountMovement() throws Exception {
		AccountMovementDto dto = new AccountMovementDto("018111", new Date(), null, "Mi descripcion",
				new BigDecimal(123.456));

		HttpEntity<AccountMovementDto> requestObject = new HttpEntity<AccountMovementDto>(dto, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/accountMovement"), HttpMethod.POST,
				requestObject, String.class);

		if (response.getStatusCode().value() == 200) {

			JSONAssert.assertEquals(
					"{\"message\": \"[AccountMovementValidationException] El TIPO DE MOVIMIENTO es obligatorio.\",\"responseType\": \"ERROR\"}",
					response.getBody(), false);
		} else {
			fail("[IntegrationTest] - Unespected Error: HTTP StatusCode: " + response.getStatusCode().value());
		}

	}

	@Test
	public void validateCreateAccountMovement() throws Exception {
		AccountMovementDto dto = new AccountMovementDto("018111", new Date(), MovementType.CREDIT, "Mi descripcion",
				new BigDecimal(123.456));

		HttpEntity<AccountMovementDto> requestObject = new HttpEntity<AccountMovementDto>(dto, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/accountMovement"), HttpMethod.POST,
				requestObject, String.class);

		if (response.getStatusCode().value() == 200) {

			JSONAssert.assertEquals("{\"message\": \"Creacion Exitosa\",\"responseType\": \"OK\"}", response.getBody(),
					false);
		} else {
			fail("[IntegrationTest] - Unespected Error: HTTP StatusCode: " + response.getStatusCode().value());
		}

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
