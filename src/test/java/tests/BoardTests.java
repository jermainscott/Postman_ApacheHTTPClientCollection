package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTests {

    private Properties prop = new Properties();
    private String SECRET_KEY;
    private String boardId;
    private final String BASE_URL = "https://api.trello.com/1/boards/";

    @BeforeTest
    public void beforeAllTests() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("secrets.properties");
        prop.load(stream);
        String API_KEY = prop.getProperty("api_key");
        String TOKEN = prop.getProperty("token");
        SECRET_KEY = "token=" + TOKEN + "&key=" + API_KEY;
    }

    @Test
    public void createBoardTest() throws IOException {
        String boardName = "testBoard_5";
        String url = BASE_URL + "?" + SECRET_KEY + "&name=" + boardName;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                int statusCode = response.getCode();
                assertThat(statusCode).isEqualTo(200);

                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> responseBody = mapper.readValue(response.getEntity().getContent(), HashMap.class);
                boardId = (String) responseBody.get("id");

                assertThat(responseBody.get("name")).isEqualTo(boardName);
            }
        }
    }

    @Test(dependsOnMethods = {"createBoardTest"})
    public void getBoardByIdTest() throws IOException {
        String url = BASE_URL + boardId + "?" + SECRET_KEY;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(httpGet)) {
                int statusCode = response.getCode();
                assertThat(statusCode).isEqualTo(200);

                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> responseBody = mapper.readValue(response.getEntity().getContent(), HashMap.class);

                assertThat(responseBody.get("id")).isEqualTo(boardId);
            }
        }
    }

    @Test(dependsOnMethods = {"getBoardByIdTest"})
    public void updateBoardTest() throws IOException {
        String updatedName = "Updated_TestBoard";
        String url = BASE_URL + boardId + "?" + SECRET_KEY + "&name=" + updatedName;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(url);
            httpPut.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(httpPut)) {
                int statusCode = response.getCode();
                assertThat(statusCode).isEqualTo(200);

                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> responseBody = mapper.readValue(response.getEntity().getContent(), HashMap.class);

                assertThat(responseBody.get("name")).isEqualTo(updatedName);
            }
        }
    }

    @Test(dependsOnMethods = {"updateBoardTest"})
    public void deleteBoardTest() throws IOException {
        String url = BASE_URL + boardId + "?" + SECRET_KEY;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(httpDelete)) {
                int statusCode = response.getCode();
                assertThat(statusCode).isEqualTo(200);

                // Optionally, reset the boardId to indicate the board has been deleted
                boardId = null;
            }
        }
    }
}
