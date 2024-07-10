package rest.controller.Ticket;

import static io.restassured.RestAssured.given;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class TicketControllerGet {
    @Test
    public void getAllTickets() {
        given()
                .when()
                .get("/ticket/{page}/{size}",0,2)
                .then()
                .statusCode(200);
    }

    @Test
    public void getTicketById() {
        UUID id = UUID.randomUUID();
        given()
                .when().get("/ticket/getById/{id}", id)
                .then()
                .statusCode(400);
    }

    @Test
    public void getTicketByUsername() {
        String username = "Pedro";
        given()
                .when()
                .get("/ticket/getByUsername/{username}/{page}/{size}",username,0,2)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
