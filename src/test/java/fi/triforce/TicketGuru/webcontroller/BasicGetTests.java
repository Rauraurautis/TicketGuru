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

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true", "server.servlet.context-path=/"})
public class BasicGetTests {

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
    //private String authRefToken;

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