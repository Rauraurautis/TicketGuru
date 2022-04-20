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

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"server.servlet.context-path=/"})


public class BasicPostTests {

    /*
        !!!READ BEFORE TESTING!!!   !!!LUE ENNEN TESTAUSTA!!!
        
        Some of the tests here are not fully automated and require functioning credentials in the test environment to PASS. Get the required credentials from management.
        Some tests require specific user privileges. Read the test name/description.
        Kaikki testit eivät ole täysin automatisoituja ja tarvitsevat toimivan käyttäjä/salasanayhdistelmän, jotta testeistä saadaan PASS. Tarkasta tarvittavat kredentiaalit.
        Jotkut testit vaativat tiettyjä käyttäjäoikeuksia. Lue testin nimi/kuvaus.
        
        ALWAYS REMOVE REAL AUTHNAME AND AUTHPASS AFTER TESTING IF UPLOADING TO A REPO!
        HÄVITÄ AINA KAIKKI AIDOT AUTENTIKOINTI-KÄYTTÄJÄNIMET JA -SALASANAT JOS PUSHAAT YLÄVIRTAAN!
    */
    private String authNameRequired = "insert_user";    //REQUIRED, TARVITAAN
    private String authPassRequired = "insert_psw";    //REQUIRED, TARVITAAN
    private String authToken;

    
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
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
    public void givenNewVenueAllParamsJsonBodyToApiVenuesWithAdminTypeCredPostReg_thenVerifyStatusAndJsonResp() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("venueName", "NewVenueSomewhere");
        requestBody.put("venueAddress", "AStreet");
        requestBody.put("venueCity", "Canada");

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
        .post("/api/venues")
        .then()
        .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("NewVenueSomewhere", response.jsonPath().getString("venueName"));
        Assertions.assertEquals("AStreet", response.jsonPath().getString("venueAddress"));
        Assertions.assertEquals("Canada", response.jsonPath().getString("venueCity"));
        
    }

    @Test
    public void givenNewSale2ParamsJsonListBodyToApiSalesWithAdminTypeCredPostReg_thenVerifyStatusAndJsonResp() {

        Response response = given()
        .headers(
            "Authorization",
            "Bearer " + authToken,
            "Content-Type", "application/json",
            "Accept",
            "application/json")
        .contentType(ContentType.JSON)
        .body("[\n" +
        "   {\n" +
        "      \"ticketTypeId\": \"" + 2 + "\",\n" +
        "      \"nrOfTickets\": \"" + 1 + "\" \n" +
        "   }\n" +
        "]")
        .when()
        .post("/api/sales")
        .then()
        .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotNull(response.jsonPath().getString("salesEventId"));
        
    }
}



