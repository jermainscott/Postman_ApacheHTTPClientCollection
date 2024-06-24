package tests;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Board;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.CheckResponseIsValid;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class First_Test {

    private Properties prop = new Properties();
    private String SECRET_KEY;
    private ObjectMapper objectMapper;


    @BeforeTest
    public void beforeAllTests() throws IOException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("secrets.properties");
        prop.load(stream);
        String API_KEY = prop.get("api_key").toString();
        String TOKEN = prop.get("token").toString();
        SECRET_KEY = "token=" + TOKEN + "&key=" + API_KEY;


        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String getSecretKey() {
        return SECRET_KEY;
    }


    @Test
    public void firstMethod() throws IOException {

        String url = "https://api.trello.com/1/members/me?" + getSecretKey();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet getRequest = new HttpGet(url);
            headers.forEach(getRequest::setHeader);

            //   BuildRequest.buildGetRequest(url, headers);

            try (CloseableHttpResponse response = httpClient.execute(getRequest)) {
                CheckResponseIsValid.checkResponseCode(response.getCode(), 200);

                // Read the response entity once
                String responseBody = EntityUtils.toString(response.getEntity());


                // Use the response body for checks
                CheckResponseIsValid.checkResponseBodyContains(responseBody, "username");

                // String responseBody = new String(response.getEntity().getContent().readAllBytes());
                Board board = objectMapper.readValue(responseBody, Board.class);
                System.out.println(board.getName());
                System.out.println(board.getUrl());
                System.out.println(board.getId());
                System.out.println(board.getClass());


            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


        }

    }
}



