package coop.tecso.examen.controller;

import static org.junit.Assert.fail;

import java.util.Date;

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
import coop.tecso.examen.common.enums.LegalEntity;
import coop.tecso.examen.dto.AccountHolderDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountHolderIntegrationTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void findNotExistAccountHolder() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/accountHolder?rut=9890001"),
				HttpMethod.GET, entity, String.class);

		if (response.getStatusCode().value() == 200) {

			JSONAssert.assertEquals(
					"{\"message\":\"No existe un Titular asociado al RUT suministrado.\",\"responseType\":\"OK\"}",
					response.getBody(), false);

			// Or Maybe we can use JSONObject evaluationObject = new
			// JSONObject(response.getBody())
			// to get json attributes from pojo to use wathever assertion

		} else {
			fail("[IntegrationTest] - Unespected Error: HTTP StatusCode: " + response.getStatusCode().value());
		}

	}

	@Test
	public void validateDataIsCompleteAccountHolderNatural() throws Exception {
		AccountHolderDto dto = new AccountHolderDto("98001234", "15440987", null, "Molina", null, null,
				LegalEntity.NATURAL);

		HttpEntity<AccountHolderDto> requestObject = new HttpEntity<AccountHolderDto>(dto, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/accountHolder"), HttpMethod.POST,
				requestObject, String.class);

		if (response.getStatusCode().value() == 200) {

			JSONAssert.assertEquals(
					"{\"message\": \"[AccountHolderValidationException] El NOMBRE es obligatorio.\",\"responseType\": \"ERROR\"}",
					response.getBody(), false);
		} else {
			fail("[IntegrationTest] - Unespected Error: HTTP StatusCode: " + response.getStatusCode().value());
		}

	}

	@Test
	public void validateDataIsCompleteAccountHolderJuridical() throws Exception {
		AccountHolderDto dto = new AccountHolderDto("98001234", null, null, null, null, new Date(),
				LegalEntity.JURIDICAL);

		HttpEntity<AccountHolderDto> requestObject = new HttpEntity<AccountHolderDto>(dto, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/accountHolder"), HttpMethod.POST,
				requestObject, String.class);

		if (response.getStatusCode().value() == 200) {

			JSONAssert.assertEquals(
					"{\"message\": \"[AccountHolderValidationException] La RAZON SOCIAL es obligatoria.\",\"responseType\": \"ERROR\"}",
					response.getBody(), false);
		} else {
			fail("[IntegrationTest] - Unespected Error: HTTP StatusCode: " + response.getStatusCode().value());
		}

	}

	@Test
	public void createNewAccountHolder() throws Exception {

		AccountHolderDto dto = new AccountHolderDto("98001234", "15440987", "Manuel", "Molina", null, null,
				LegalEntity.NATURAL);

		HttpEntity<AccountHolderDto> requestObject = new HttpEntity<AccountHolderDto>(dto, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/accountHolder"), HttpMethod.POST,
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
