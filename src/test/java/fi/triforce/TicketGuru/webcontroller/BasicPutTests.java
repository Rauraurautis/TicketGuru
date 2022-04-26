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
import io.restassured.http.ContentType;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"server.servlet.context-path=/"})


public class BasicPutTests {

    /*
    TESTI KYSYY AJOSSA KONSOLISSA KÄYTTÄJÄTUNNUKSIA. ÄLÄ KIRJOITA NIITÄ KOODIIN. TESTAUSYMPÄRISTÖNÄ TOIMII ECLIPSE IDE. KAIKKI IDE:T EIVÄT VÄLTTÄMÄTTÄ SALLI TESTISSÄ KONSOLIIN KIRJOITTAMISTA.
    */
	
    private String authNameRequired;
    private String authPassRequired;
    private String actualTicketCode = "insert_ticketcode"; //REQUIRED ONCE IN THE DATABASE, TARVITAAN KERRAN TIETOKANNASSA
    private String authToken;

    
    @LocalServerPort
    private int port;
    public void GetCredentialsInConsole() {
    	Scanner inputScanner = new Scanner(System.in);
    	System.out.println("anna auth nimi");
    	authNameRequired = inputScanner.nextLine();
    	System.out.println("anna auth salasana");
    	authPassRequired = inputScanner.nextLine();	
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
    }

    @Test
    public void giventicketCodeToApiEventsTicketsWithTicketInspectorTypeCredPostReg_thenVerifyStatusAndJsonResp() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("ticketCode", actualTicketCode);


        Response response = given()
        .headers(
            "Authorization",
            "Bearer " + authToken,
            "Content-Type", "application/json",
            "Accept",
            "application/json")
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .put("/api/events/tickets")
        .then()
        .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(true, response.jsonPath().getString("ticketUsed"));        
    }
}
