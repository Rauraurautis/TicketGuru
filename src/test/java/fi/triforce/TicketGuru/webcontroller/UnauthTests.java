package fi.triforce.TicketGuru.webcontroller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"server.servlet.context-path=/"})

public class UnauthTests {

    /*
        These tests are here so we can be sure that users with bad credentials or bad intent are stopped from doing what they're trying to do.
        Nämä testit ovat sitä varten, jotta voidaan olla varmoja, että kun väärä käyttäjä pyrkii pääsemään häneltä kiellettyyn resurssiin tavalla tai toisella, niin hänet estetään.
    */
	
    /*
    TESTI KYSYY AJOSSA KONSOLISSA KÄYTTÄJÄTUNNUKSIA. ÄLÄ KIRJOITA NIITÄ KOODIIN. TESTAUSYMPÄRISTÖNÄ TOIMII ECLIPSE IDE. KAIKKI IDE:T EIVÄT VÄLTTÄMÄTTÄ SALLI TESTISSÄ KONSOLIIN KIRJOITTAMISTA.
    */

    private String ticketInspectorAuthNameRequired = "insert_ticketinspectoruser";
    private String ticketInspectorAuthPassRequired = "insert_ticketinspectorpsw";
    private String authToken;

    @LocalServerPort
    private int port;
    public void GetCredentialsInConsole() {
    	Scanner inputScanner = new Scanner(System.in);
    	System.out.println("anna auth nimi TicketInspector roolilla");
    	ticketInspectorAuthNameRequired = inputScanner.nextLine();
    	System.out.println("anna auth salasana TicketInscpector roolilla");
    	ticketInspectorAuthPassRequired = inputScanner.nextLine();	
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void givenCredsInWrongURIEventsWithNoTokenPostReq_thenVerifyStatusAndMessage() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body("{\n" +
                "   \"username\": \"wrongname\",\n" +
                "   \"password\": \"3059dwrongpass68sdig\" \n}")
                .post("/events")
                .then()
                .extract().response();

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Missing token or the password / username was false", response.jsonPath().getString("Error"));
    }
    
    @Test
    public void givenNewVenueAllParamsJsonBodyToApiVenuesWithWrongTokenPostReg_thenVerifyStatusAndJsonResp() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("venueName", "asdf");
        requestBody.put("venueAddress", "ghjk");
        requestBody.put("venueCity", "ruotsi");

        Response response = given()
        .headers(
            "Authorization",
            "Bearer " + "thisIsClearlyNotTheRightToken",
            "Content-Type", "application/json",
            "Accept",
            "application/json")
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .post("/api/venues")
        .then()
        .extract().response();

        Assertions.assertEquals(403, response.statusCode());
        Assertions.assertNotEquals("asdf", response.jsonPath().getString("venueName"));
        Assertions.assertNotEquals("ghjk", response.jsonPath().getString("venueAddress"));
        Assertions.assertNotEquals("ruotsi", response.jsonPath().getString("venueCity"));
        
    }

    @Test
    public void givenApiEventsIdWithWrongCredTypeDeleteReg_thenVerifyStatusAndJsonResp() {  //TEST NEEDS TICKETINSPECTOR-TYPE CREDENTIALS TO BE OF ANY USE; TESTI TARVITSEE LIPUNMYYJÄN KREDENTIAALIT OLLAKSEEN HYÖDYLLINEN
    	GetCredentialsInConsole();
        Response authResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body("{\n" +
                "   \"username\": \"" + ticketInspectorAuthNameRequired + "\",\n" +
                "   \"password\": \"" + ticketInspectorAuthPassRequired + "\" \n}")
                .post("/login")
                .then()
                .extract().response();
        authToken = authResponse.jsonPath().getString("access_token");

        Response finalResponse = given()
        .headers(
            "Authorization",
            "Bearer " + authToken,
            "Content-Type", "application/json",
            "Accept",
            "application/json")
        .delete("/api/events/1")
        .then()
        .extract().response();

        Assertions.assertEquals(403, finalResponse.statusCode());
    }

}
