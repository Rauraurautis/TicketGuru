package fi.triforce.TicketGuru.webcontroller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Scanner;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true", "server.servlet.context-path=/"})
public class LoginTest {

    /*
        TESTI KYSYY AJOSSA KONSOLISSA KÄYTTÄJÄTUNNUKSIA. ÄLÄ KIRJOITA NIITÄ KOODIIN. TESTAUSYMPÄRISTÖNÄ TOIMII ECLIPSE IDE. KAIKKI IDE:T EIVÄT VÄLTTÄMÄTTÄ SALLI TESTISSÄ KONSOLIIN KIRJOITTAMISTA.
    */
    private String authNameRequired;
    private String authPassRequired;
    private Scanner inputScanner;
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void givenLoginURIAndSendingWrongCredPostReq_thenVerifyStatusAndEMessage() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body("{\n" +
                "   \"username\": \"wrongname\",\n" +
                "   \"password\": \"3059dwrongpass68sdig\" \n}")
                .post("/login")
                .then()
                .extract().response();

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Missing token or the password / username was false", response.jsonPath().getString("Error"));
    }

    @Test
    public void givenLoginURIAndSendingRightAnyTypeCredPostReq_thenVerifyStatus() {
    	inputScanner = new Scanner(System.in);
    	System.out.println("anna auth nimi");
    	authNameRequired = inputScanner.nextLine();
    	System.out.println("anna auth salasana");
    	authPassRequired = inputScanner.nextLine();
    	inputScanner.close();
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body("{\n" +
                "   \"username\": \"" + authNameRequired + "\",\n" +
                "   \"password\": \"" + authPassRequired + "\" \n}")
                .post("/login")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.jsonPath().getString("access_token"));
        Assertions.assertNotNull(response.jsonPath().getString("refresh_token"));
    }
}