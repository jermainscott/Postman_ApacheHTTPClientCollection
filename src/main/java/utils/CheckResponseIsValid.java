package utils;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.assertj.core.api.Assertions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CheckResponseIsValid {

    public static void checkResponseCode(CloseableHttpResponse response, int expectedCode) {
        Assertions.assertThat(response.getCode()).as("Check response status code").isEqualTo(expectedCode);
    }

    public static void checkResponseCode(int response, int expectedCode) {
        Assertions.assertThat(response).as("Check response status code").isEqualTo(expectedCode);
    }

    public static void checkResponseBodyContains(CloseableHttpResponse response, String expectedContent) throws IOException {
        String responseBody = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        Assertions.assertThat(responseBody).as("Check response body contains").contains(expectedContent);
    }

    public static void checkResponseBodyContains(String responseBody, String expectedContent) throws IOException {
        Assertions.assertThat(responseBody).as("Check response body contains").contains(expectedContent);
    }

    public static void checkBoardFields(CloseableHttpResponse response, String expectedName) throws IOException {
        String responseBody = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        Assertions.assertThat(responseBody).contains("\"name\":\"" + expectedName + "\"");
        Assertions.assertThat(responseBody).contains("\"id\":\"");
    }
}
