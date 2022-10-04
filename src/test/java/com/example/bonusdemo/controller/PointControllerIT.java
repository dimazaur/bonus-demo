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
public class PointControllerIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void before() {
        headers.add("Content-Type", "application/json");
    }

    @Test
    public void testCalculatePointsTotal() throws Exception {
        //Given
        HttpEntity<String> entity = new HttpEntity<>("[{\"userId\": \"ITUSER4\",\"amount\": 25.00,\"timestamp\": \"2022-08-01 01:01:01\"}," +
                "{\"userId\": \"ITUSER4\",\"amount\": 75.00,\"timestamp\": \"2022-09-01 01:01:01\"}," +
                "{\"userId\": \"ITUSER4\",\"amount\": 150.00,\"timestamp\": \"2022-09-15 01:01:01\"}]", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transaction/list"),
                HttpMethod.POST, entity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);


        HttpEntity<String> entity2 = new HttpEntity<>(null, headers);

        //When
        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/points/total/ITUSER4"),
                HttpMethod.GET, entity2, String.class);


        //Then
        String expected = "{\"points\":175.0}";
        assertEquals(response2.getStatusCode(), HttpStatus.OK);
        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    public void testCalculatePointsByDate() throws Exception {
        //Given
        HttpEntity<String> entity = new HttpEntity<>("[{\"userId\": \"ITUSER5\",\"amount\": 25.00,\"timestamp\": \"2022-08-01 01:01:01\"}," +
                "{\"userId\": \"ITUSER5\",\"amount\": 75.00,\"timestamp\": \"2022-09-01 01:01:01\"}," +
                "{\"userId\": \"ITUSER5\",\"amount\": 150.00,\"timestamp\": \"2022-09-15 01:01:01\"}]", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transaction/list"),
                HttpMethod.POST, entity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);


        HttpEntity<String> entity2 = new HttpEntity<>(null, headers);

        //When
        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/points/ITUSER5?dateFrom=2022-08-01T00:00:00&dateTo=2022-09-02T00:00:00"),
                HttpMethod.GET, entity2, String.class);

        //Then
        String expected = "{\"points\":25.0}";
        assertEquals(response2.getStatusCode(), HttpStatus.OK);
        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    public void testTransactionsNotFoundTotal() throws Exception {
        //Given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/points/total/ITUSER7"),
                HttpMethod.GET, entity, String.class);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody(), "404 NOT_FOUND \"Transactions not found\"");
    }

    @Test
    public void testTransactionsNotFoundByDates() throws Exception {
        //Given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/points/ITUSER8?dateFrom=2022-08-01T00:00:00&dateTo=2022-09-02T00:00:00"),
                HttpMethod.GET, entity, String.class);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody(), "404 NOT_FOUND \"Transactions not found\"");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}