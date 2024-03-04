package ru.inno.web;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class SimpleRequest {

    public static void main(String[] args) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpGet getRequest = new HttpGet("https://crudcrud.com/api/b0e5c03f47e44d439e82f3c8c89b3666/unicorns");

        HttpResponse response = client.execute(getRequest);

        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getStatusLine().getReasonPhrase());
        System.out.println(response.getStatusLine().getProtocolVersion());

    }
}
