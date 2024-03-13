package ru.inno.x_clients;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class XClientsContractTest {

    public static final String URL = "https://x-clients-be.onrender.com/company";
    public static final String URL_ID = "https://x-clients-be.onrender.com/company/{id}";
    public static final String URL_AUTH = "https://x-clients-be.onrender.com/auth/login";

    public static  String TOKEN;

    @BeforeAll
    public static void getToken(){
        String creds = """
                {
                  "username": "bloom",
                  "password": "fire-fairy"
                }
                """;

        // авторизоваться
        TOKEN = given().log().all()
                .body(creds)
                .contentType(ContentType.JSON)
                .when().post(URL_AUTH)
                .then().log().all()
                .statusCode(201)
                .extract().path("userToken");
    }

    @Test
    public void shouldReturnListOfCompanies() {

        given() // настраиваем запрос
                .log().all()
                .header("ABC", "123")
                .get(URL)// пришел ответ
                .then()// работа с результатом -> проверка ответа
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Vary", "Accept-Encoding")
                .body(is(notNullValue()))
                .log().all();
    }

    @Test
    public void iCanCreateCompany() {
        String myJson = """ 
                {
                  "name": "Иннополис университет"
                }
                """;

        // создать компанию
        int newCompanyId = given()
                .log().all()
                .body(myJson)
                .header("x-client-token", TOKEN)
                .contentType(ContentType.JSON)
                .when().post(URL)
                .then()
                .log().all()
                .statusCode(201)
                .body("id", greaterThan(0))
                .extract().path("id");

        // получить компанию и проверить название
        given()
                .log().all()
//                .get(URL_ID, newCompanyId)
                .pathParams("id", newCompanyId)
                .get(URL_ID)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("Иннополис университет"));
    }
}
