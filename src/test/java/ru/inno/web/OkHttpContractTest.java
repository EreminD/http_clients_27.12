package ru.inno.web;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.web.extention.MyFileLogger;
import ru.inno.web.extention.MyInterceptor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OkHttpContractTest {

    private static final String URL = "https://todo-app-sky.herokuapp.com";

    private OkHttpClient client;

    @BeforeEach
    public void setUp() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new MyFileLogger());
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient().newBuilder()
                .addInterceptor(new MyInterceptor())
                .addInterceptor(interceptor)
                .build();
    }

    @Test
    @DisplayName("Получение списка задач. Проверяем статус-код и заголовок Content-Type")
    public void shouldReceive200OnGetRequest() throws IOException {
        Request getAll = new Request.Builder().url(URL).build();

        Response response = client.newCall(getAll).execute();
        String body = response.body().string();

//         Проверить поля ответа
        assertEquals(200, response.code());
        assertEquals("application/json; charset=utf-8", response.headers().get("Content-Type"));
        assertTrue(body.startsWith("["));
        assertTrue(body.endsWith("]"));
    }

    @Test
    @DisplayName("Создание задачи. Проверяем статус-код, заголовок Content-Type и тело ответа содержит json")
    public void shouldReceive201OnPostRequest() throws IOException {
        String myContent = "{\"title\" : \"test\"}";
        RequestBody reqBody = RequestBody.create(myContent, MediaType.get("application/json"));

        Request createTaskReq = new Request.Builder()
                .header("ABC", "123")
                .post(reqBody)
                .url(URL)
                .build();

        Response response = client.newCall(createTaskReq).execute();
        String body = response.body().string();

        // Проверить поля ответ
        assertEquals(201, response.code());
        assertTrue(body.startsWith("{"));
        assertTrue(body.endsWith("}"));
    }

}
