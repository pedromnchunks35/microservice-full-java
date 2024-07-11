package rest.utils.date;

import rest.dto.TicketDTO;
import java.util.Calendar;

public class TicketDateUtils {
    public static void generateTicketDTO(TicketDTO ticketDTO, Calendar currentTime) {
        Calendar c = currentTime;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int year = c.get(Calendar.YEAR);
        int timeToSum = 1;
        // ? If time is superior than 10 minutes we add 2 hours instead of 1
        if (minute > 10) {
            timeToSum++;
        }
        // ? Case the hour becomes higher than 24
        if (hour + timeToSum > 24) {
            // ? We set the time for the difference of that result with the max hours
            hour = hour + timeToSum - 24;
            // ? Now we get the max number of days this month can have
            Calendar fakeCalender = currentTime;
            fakeCalender.set(Calendar.MONTH, fakeCalender.get(Calendar.MONTH) + 1);
            fakeCalender.set(Calendar.DAY_OF_MONTH, 1);
            fakeCalender.add(Calendar.DAY_OF_MONTH, -1);
            int maxNumberOfDays = fakeCalender.get(Calendar.DAY_OF_MONTH);
            if (c.get(Calendar.DAY_OF_MONTH) == maxNumberOfDays) {
                month++;
                if (month > 11) {
                    month = 0;
                    year++;
                }
            }
        } else {
            hour += timeToSum;
        }
        ticketDTO.setDay((short) day);
        ticketDTO.setHour((short) hour);
        ticketDTO.setMonth((short) (month + 1));
        ticketDTO.setYear((short) year);
    }
}
