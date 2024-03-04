package ru.inno.web;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpTests {

    private HttpClient client = HttpClientBuilder.create().build();
    public static final String URL = "https://crudcrud.com";

    @Test
    public void canGetStatusCode() throws IOException {
        HttpGet getRequest = new HttpGet(URL+"/api/b0e5c03f47e44d439e82f3c8c89b3666/unicorns");

        HttpResponse response = client.execute(getRequest);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void contentTypeShouldBeJson() throws IOException {
        HttpGet getRequest = new HttpGet(URL+"/api/b0e5c03f47e44d439e82f3c8c89b3666/unicorns");

        HttpResponse response = client.execute(getRequest);

        assertTrue(response.containsHeader("Content-Type"));

        Header contentTypeHeader = response.getFirstHeader("Content-Type");
        assertTrue(contentTypeHeader.getValue().contains("application/json"));
    }

    @Test
    public void shouldContainStarUnicorn() throws IOException {
        HttpGet getRequest = new HttpGet(URL+"/api/b0e5c03f47e44d439e82f3c8c89b3666/unicorns");
        HttpResponse response = client.execute(getRequest);
        String responseBody = EntityUtils.toString(response.getEntity());

        // jackson json -> Obj
        assertTrue(responseBody.contains("Звездочка"));
    }

    @Test
    public void canCreateUnicorn() throws IOException {
        String requestBody = "{\"name\": \"Sparkle Angel\", \"age\": 2,\"colour\": \"blue\"}";
        HttpPost createUnicorn = new HttpPost(URL+"/api/b0e5c03f47e44d439e82f3c8c89b3666/unicorns");
        createUnicorn.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        HttpResponse response = client.execute(createUnicorn);
        String responseBody = EntityUtils.toString(response.getEntity());
        assertEquals(201, response.getStatusLine().getStatusCode());
    }
}
