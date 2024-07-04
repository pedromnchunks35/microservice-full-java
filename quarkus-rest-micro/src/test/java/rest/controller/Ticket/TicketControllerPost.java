package rest.controller.Ticket;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class TicketControllerPost {
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
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body("day", equalTo(1))
                .body("hour", equalTo(2))
                .body("month", equalTo(3))
                .body("year", equalTo(2000));
    }

}
