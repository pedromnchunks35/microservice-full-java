package rest.controller.Ticket;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class TicketControllerPost {
    // ? Transactional annotation make the changes in the db take a rollback
    @Transactional
    @Test
    public void createTicket() {
        JSONObject ticketDTO = new JSONObject();
        ticketDTO.put("day", 1);
        ticketDTO.put("hour", 2);
        ticketDTO.put("month", 3);
        ticketDTO.put("year", 2000);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ticketDTO.toString())
                .when().post("/ticket")
                .then()
                .statusCode(400);
    }

}
