package rest.controller.Ticket;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class TicketControllerDelete {
    @Test
    public void deleteTicket() {
        UUID id = UUID.randomUUID();
        given()
                .pathParam("id", id)
                .when().delete("/ticket/{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(equalTo(id.toString()));
    }
}
