package com.example.bonusdemo.controller;

import com.example.bonusdemo.SpringDemoApplication;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void before() {
        headers.add("Content-Type", "application/json");
    }

    @Test
    public void testAddTransaction() throws Exception {
        //Given
        HttpEntity<String> entity = new HttpEntity<>("{\"userId\": \"ITUSER1\",\"amount\": 75.00,\"timestamp\": \"2022-10-01 01:01:01\"}", headers);

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transaction"),
                HttpMethod.POST, entity, String.class);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testAddWrongTransaction() throws Exception {
        //Given
        HttpEntity<String> entity = new HttpEntity<>("{\"amount\": 75.00,\"timestamp\": \"2022-10-01 01:01:01\"}", headers);

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transaction"),
                HttpMethod.POST, entity, String.class);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAddTransactionList() throws Exception {
        //Given
        HttpEntity<String> entity = new HttpEntity<>("[{\"userId\": \"ITUSER1\",\"amount\": 25.00,\"timestamp\": \"2022-10-01 01:01:01\"}," +
                "{\"userId\": \"ITUSER1\",\"amount\": 75.00,\"timestamp\": \"2022-10-02 01:01:01\"}," +
                "{\"userId\": \"ITUSER1\",\"amount\": 150.00,\"timestamp\": \"2022-10-03 01:01:01\"}]", headers);

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transaction/list"),
                HttpMethod.POST, entity, String.class);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


    @Test
    public void testGetTransactionList() throws Exception {
        //Given
        HttpEntity<String> entity = new HttpEntity<>("{\"userId\": \"ITUSER2\",\"amount\": 75.00,\"timestamp\": \"2022-10-01 01:01:01\"}", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transaction"),
                HttpMethod.POST, entity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);


        HttpEntity<String> entity2 = new HttpEntity<>(null, headers);

        //When
        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/transaction/list/ITUSER2"),
                HttpMethod.GET, entity2, String.class);

        String expected = "[{\"userId\":\"ITUSER2\",\"amount\":75.00,\"timestamp\":\"2022-10-01 01:01:01\"}]";

        //Then
        assertEquals(response2.getStatusCode(), HttpStatus.OK);
        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    public void testGetTransactionDatePeriod() throws Exception {
        //Given
        HttpEntity<String> entity = new HttpEntity<>("[{\"userId\": \"ITUSER3\",\"amount\": 25.00,\"timestamp\": \"2022-08-01 01:01:01\"}," +
                "{\"userId\": \"ITUSER3\",\"amount\": 75.00,\"timestamp\": \"2022-09-01 01:01:01\"}," +
                "{\"userId\": \"ITUSER3\",\"amount\": 150.00,\"timestamp\": \"2022-09-15 01:01:01\"}]", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transaction/list"),
                HttpMethod.POST, entity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        HttpEntity<String> entity2 = new HttpEntity<>(null, headers);

        //When
        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/transaction/date/ITUSER3?dateFrom=2022-09-01T00:00:00&dateTo=2022-10-01T00:00:00"),
                HttpMethod.GET, entity2, String.class);

        String expected = "[{\"userId\":\"ITUSER3\",\"amount\":75.00,\"timestamp\":\"2022-09-01 01:01:01\"}," +
                "{\"userId\":\"ITUSER3\",\"amount\":150.00,\"timestamp\":\"2022-09-15 01:01:01\"}]";

        System.out.println("response2.getBody() = " + response2.getBody());

        //Then
        assertEquals(response2.getStatusCode(), HttpStatus.OK);
        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}