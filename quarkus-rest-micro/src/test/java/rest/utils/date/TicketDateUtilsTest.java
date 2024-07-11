package rest.utils.date;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import rest.dto.TicketDTO;

import java.util.Calendar;

@QuarkusTest
public class TicketDateUtilsTest {

    @Test
    public void generateTicketDTOTest() {
        Calendar currentTime = Calendar.getInstance();
        // ? MOCK
        Calendar toMock = currentTime;
        toMock.set(Calendar.DAY_OF_MONTH, 1);
        toMock.set(Calendar.MONTH, 0);
        toMock.set(Calendar.HOUR, 10);
        toMock.set(Calendar.MINUTE, 12);
        toMock.set(Calendar.YEAR, 2024);
        // ? Make the generation of ticket
        TicketDTO ticketDTO = new TicketDTO();
        TicketDateUtils.generateTicketDTO(ticketDTO, toMock);
        assertEquals(ticketDTO.getDay(), 1);
        assertEquals(ticketDTO.getMonth(), 1);
        assertEquals(ticketDTO.getHour(), 12);
        assertEquals(ticketDTO.getYear(), 2024);

        // ? SECOND CASE
        toMock.set(Calendar.DAY_OF_MONTH, 1);
        toMock.set(Calendar.MONTH, 0);
        toMock.set(Calendar.HOUR, 10);
        toMock.set(Calendar.MINUTE, 10);
        toMock.set(Calendar.YEAR, 2024);
        TicketDateUtils.generateTicketDTO(ticketDTO, toMock);
        assertEquals(ticketDTO.getDay(), 1);
        assertEquals(ticketDTO.getMonth(), 1);
        assertEquals(ticketDTO.getHour(), 11);
        assertEquals(ticketDTO.getYear(), 2024);

        // ? THIRD CASE
        toMock.set(Calendar.DAY_OF_MONTH, 1);
        toMock.set(Calendar.MONTH, 0);
        toMock.set(Calendar.HOUR, 10);
        toMock.set(Calendar.MINUTE, 59);
        toMock.set(Calendar.YEAR, 2024);
        TicketDateUtils.generateTicketDTO(ticketDTO, toMock);
        assertEquals(ticketDTO.getDay(), 1);
        assertEquals(ticketDTO.getMonth(), 1);
        assertEquals(ticketDTO.getHour(), 12);
        assertEquals(ticketDTO.getYear(), 2024);

         // ? FORTH CASE
         toMock.set(Calendar.DAY_OF_MONTH, 1);
         toMock.set(Calendar.MONTH, 0);
         toMock.set(Calendar.HOUR, 24);
         toMock.set(Calendar.MINUTE, 20);
         toMock.set(Calendar.YEAR, 2024);
         TicketDateUtils.generateTicketDTO(ticketDTO, toMock);
         assertEquals(ticketDTO.getDay(), 2);
         assertEquals(ticketDTO.getMonth(), 1);
         assertEquals(ticketDTO.getHour(), 2);
         assertEquals(ticketDTO.getYear(), 2024);

          // ? FIFTH CASE
          toMock.set(Calendar.DAY_OF_MONTH, 1);
          toMock.set(Calendar.MONTH, 0);
          toMock.set(Calendar.HOUR, 24);
          toMock.set(Calendar.MINUTE, 10);
          toMock.set(Calendar.YEAR, 2024);
          TicketDateUtils.generateTicketDTO(ticketDTO, toMock);
          assertEquals(ticketDTO.getDay(), 2);
          assertEquals(ticketDTO.getMonth(), 1);
          assertEquals(ticketDTO.getHour(), 1);
          assertEquals(ticketDTO.getYear(), 2024);

          // ? SIXTH CASE
          toMock.set(Calendar.DAY_OF_MONTH, 31);
          toMock.set(Calendar.MONTH, 6);
          toMock.set(Calendar.HOUR, 24);
          toMock.set(Calendar.MINUTE, 10);
          toMock.set(Calendar.YEAR, 2024);
          TicketDateUtils.generateTicketDTO(ticketDTO, toMock);
          assertEquals(ticketDTO.getDay(), 1);
          assertEquals(ticketDTO.getMonth(), 8);
          assertEquals(ticketDTO.getHour(), 1);
          assertEquals(ticketDTO.getYear(), 2024);

          // ? SEPTH CASE
          toMock.set(Calendar.DAY_OF_MONTH, 31);
          toMock.set(Calendar.MONTH, 11);
          toMock.set(Calendar.HOUR, 24);
          toMock.set(Calendar.MINUTE, 10);
          toMock.set(Calendar.YEAR, 2024);
          TicketDateUtils.generateTicketDTO(ticketDTO, toMock);
          assertEquals(ticketDTO.getDay(), 1);
          assertEquals(ticketDTO.getMonth(), 1);
          assertEquals(ticketDTO.getHour(), 1);
          assertEquals(ticketDTO.getYear(), 2025);
    }
}
