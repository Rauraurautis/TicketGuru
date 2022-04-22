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
public class BasicGetTests {

    /*
    TESTI KYSYY AJOSSA KONSOLISSA KÄYTTÄJÄTUNNUKSIA. ÄLÄ KIRJOITA NIITÄ KOODIIN. TESTAUSYMPÄRISTÖNÄ TOIMII ECLIPSE IDE. KAIKKI IDE:T EIVÄT VÄLTTÄMÄTTÄ SALLI TESTISSÄ KONSOLIIN KIRJOITTAMISTA.
    */
	
    private String authNameRequired;
    private String authPassRequired;
    private String authToken;
    //private String authRefToken;

    @LocalServerPort
    private int port;
    public void GetCredentialsInConsole() {
    	Scanner inputScanner = new Scanner(System.in);
    	System.out.println("anna auth nimi");
    	authNameRequired = inputScanner.nextLine();
    	System.out.println("anna auth salasana");
    	authPassRequired = inputScanner.nextLine();
    	inputScanner.close();
    }
    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    	GetCredentialsInConsole();
        Response authResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body("{\n" +
                "   \"username\": \"" + authNameRequired + "\",\n" +
                "   \"password\": \"" + authPassRequired + "\" \n}")
                .post("/login")
                .then()
                .extract().response();
        authToken = authResponse.jsonPath().getString("access_token");
        //authRefToken = authResponse.jsonPath().getString("refresh_token");
    }

    @Test
    public void givenBTokenToApiVenuesGetReq_thenVerifyStatus() {
        Response response = given()
                .headers(
                    "Authorization",
                    "Bearer " + authToken,
                    "Content-Type", "application/json",
                    "Accept",
                    "application/json")
                .get("/api/venues")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.jsonPath().getString("venueName"));
    }

}