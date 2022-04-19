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

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"server.servlet.context-path=/"})

public class UnauthTests {

    /*
        These tests are here so we can be sure that users with bad credentials or bad intent are stopped from doing what they're trying to do.
        Nämä testit ovat sitä varten, jotta voidaan olla varmoja, että kun väärä käyttäjä pyrkii pääsemään häneltä kiellettyyn resurssiin tavalla tai toisella, niin hänet estetään.
    */
    /*
    !!!READ BEFORE TESTING!!!   !!!LUE ENNEN TESTAUSTA!!!
        
        Some of the tests here are not fully automated and require functioning credentials in the test environment to PASS. Get the required credentials from management.
        Some tests require specific user privileges. Read the test name/description.
        Kaikki testit eivät ole täysin automatisoituja ja tarvitsevat toimivan käyttäjä/salasanayhdistelmän, jotta testeistä saadaan PASS. Tarkasta tarvittavat kredentiaalit.
        Jotkut testit vaativat tiettyjä käyttäjäoikeuksia. Lue testin nimi/kuvaus.

        ALWAYS REMOVE REAL AUTHNAME AND AUTHPASS AFTER TESTING IF UPLOADING TO A REPO!
        HÄVITÄ AINA KAIKKI AIDOT AUTENTIKOINTI-KÄYTTÄJÄNIMET JA -SALASANAT JOS PUSHAAT YLÄVIRTAAN!
    */

    private String ticketInspectorAuthNameRequired = "insert_ticketinspectoruser";    //REQUIRED, TARVITAAN
    private String ticketInspectorAuthPassRequired = "insert_ticketinspectorpsw";    //REQUIRED, TARVITAAN
    private String authToken;

    @LocalServerPort
    private int port;

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
