package coop.tecso.examen.controller;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

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
import coop.tecso.examen.dto.AccountDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountIntegrationTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void validateDataIsCompleteNewAccount() throws Exception {
		AccountDto dto = new AccountDto("0190003", null, new BigDecimal(1300.4556));

		HttpEntity<AccountDto> requestObject = new HttpEntity<AccountDto>(dto, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/account"), HttpMethod.POST,
				requestObject, String.class);

		if (response.getStatusCode().value() == 200) {

			JSONAssert.assertEquals(
					"{\"message\": \"[AccountValidationException] La MONEDA es obligatoria.\",\"responseType\": \"ERROR\"}",
					response.getBody(), false);
		} else {
			fail("[IntegrationTest] - Unespected Error: HTTP StatusCode: " + response.getStatusCode().value());
		}

	}

	@Test
	public void validateCreateNewAccount() throws Exception {
		AccountDto dto = new AccountDto("0190222", Currency.EURO, new BigDecimal(1300.4556));

		HttpEntity<AccountDto> requestObject = new HttpEntity<AccountDto>(dto, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/account"), HttpMethod.POST,
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
