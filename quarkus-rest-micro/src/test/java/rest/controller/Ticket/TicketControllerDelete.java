package rest.controller.Ticket;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TicketControllerDelete {
    @Test
    public void deleteTicket() {
        UUID id = UUID.randomUUID();
        given()
                .pathParam("id", id)
                .when().delete("/ticket/{id}")
                .then()
                .statusCode(400);
    }
}
