package ru.inno.web.extention;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.logging.impl.Log4JLogger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class MyInterceptor implements Interceptor {

    public Response intercept(@NotNull Chain chain) throws IOException {

        // Test --> Interceptor (request) --> Web
        Request request = chain.request();

        System.out.println("ОТПРАЯВИЛИ ЗАПРОС");

        request.headers().toMultimap().put("test", List.of("1"));
        System.out.println(request.method() + " " + request.url());

        // Test --> Interceptor --> Web(request)
        // Test <-- Interceptor (response) <-- Web
        Response response = chain.proceed(request);

        System.out.println("ПОЛУЧИЛИ ОТВЕТ");
        System.out.println(response.code());

        // Test (response) <-- Interceptor <-- Web
        return response;
    }
}
