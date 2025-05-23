package ru.netology.testmode;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import java.util.Locale;
import static io.restassured.RestAssured.given;

public class DataGenerator {
        private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static void sendRequest(RegistrationDto user) {
        // TODO: отправить запрос на указанный в требованиях path, передав в body запроса объект user
        //  и не забудьте передать подготовленную спецификацию requestSpec.
        //  Пример реализации метода показан в условии к задаче.
        given()
                .spec(requestSpec)
                .body(new RegistrationDto(
                        user.getLogin(),
                        user.getPassword(),
                        user.getStatus()))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

    }

    public static String getRandomLogin() {
        String login = faker.name().firstName();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);

        }

        public static RegistrationDto getRegisteredUser(String status) {
            var getRegisteredUser = getUser(status);
            sendRequest(getRegisteredUser);
            return getRegisteredUser;
        }
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;

    }
}
