package rest.controller.Ticket;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class TicketControllerGet {
    @Test
    public void getAllTickets() {
        given()
                .when().get("/ticket")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("day", equalTo(1));
    }

    @Test
    public void getTicketById() {
        UUID id = UUID.randomUUID();
        given()
                .when().get("/ticket/getById/{id}",id)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(equalTo(id.toString()));
    }

    @Test
    public void getTicketByUsername() {
        String username = "Pedro";
        given()
                .when().get("/ticket/getByUsername/{username}",username)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(equalTo(username));
    }
}
