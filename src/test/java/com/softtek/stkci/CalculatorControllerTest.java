package com.softtek.stkci;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.softtek.stkci.model.ApiResult;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class CalculatorControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Mock
	ApiResult apiResult;

	@Test
	public void init() throws Exception {

		ResponseEntity<String> response = restTemplate
				.getForEntity(new URL("http://localhost:" + port + "/calculator/").toString(), String.class);
		assertEquals("CalculatorController startup", response.getBody());

	}

	@Test
	public void add() throws Exception {

		when(apiResult.getResult()).thenReturn("7");
		System.out.println("apiResult.getResult()" + apiResult.getResult());
		String expectedJson = "{\"result\":\"7\",\"error\":null}";
		ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/calculator/add?augend=2&addend=5").toString(), String.class);
		assertEquals(expectedJson, response.getBody());

	}

	@Test
	public void subtract() throws Exception {

		String expectedJson = "{\"result\":\"3\",\"error\":null}";
		ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/calculator/subtract?minuent=7&subtrahend=4").toString(),
				String.class);
		assertEquals(expectedJson, response.getBody());

	}

	@Test
	public void multiply() throws Exception {

		String expectedJson = "{\"result\":\"28\",\"error\":null}";
		ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/calculator/multiply?multiplier=7&multiplicand=4").toString(),
				String.class);
		assertEquals(expectedJson, response.getBody());

	}

	@Test
	public void divide() throws Exception {

		String expectedJson = "{\"result\":\"3\",\"error\":null}";
		ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/calculator/divide?dividend=9&divisor=3").toString(),
				String.class);
		assertEquals(expectedJson, response.getBody());

	}

	@Test
	public void divisorbyZero() throws Exception {

		String expectedJson = "{\"result\":null,\"error\":\"No division by null\"}";
		ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/calculator/divide?dividend=9&divisor=0").toString(),
				String.class);
		assertEquals(expectedJson, response.getBody());

	}
}
