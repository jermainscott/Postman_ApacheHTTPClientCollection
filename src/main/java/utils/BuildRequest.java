package utils;


import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.util.Map;

public class BuildRequest {


    public static HttpPost buildPostRequest(String url, String body, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        setHeaders(post, headers);
        post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        return post;
    }

    public static HttpGet buildGetRequest(String url, Map<String, String> headers) {
        HttpGet get = new HttpGet(url);
        setHeaders(get, headers);
        return get;
    }

    public static HttpPut buildPutRequest(String url, String body, Map<String, String> headers) {
        HttpPut put = new HttpPut(url);
        setHeaders(put, headers);
        put.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        return put;
    }

    public static HttpDelete buildDeleteRequest(String url, Map<String, String> headers) {
        HttpDelete delete = new HttpDelete(url);
        setHeaders(delete, headers);
        return delete;
    }

    private static void setHeaders(ClassicHttpRequest request, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(request::setHeader);
        }
    }
}
