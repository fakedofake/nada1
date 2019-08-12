package payroll.paymentschedule;

import payroll.paymentschedule.calendar.Calendar;

import java.security.InvalidParameterException;

public class PScheduleFactory {
    private Calendar calendar;

    public PScheduleFactory(Calendar calendar){
        this.calendar = calendar;
    }

    public PaymentSchedule createPaymentSchedule(String description){
        description = handleDefaultSchedule(description);
        PaymentSchedule pschedule;
        String type = getType(description);
        if (type.equals("monthly")){
            int day = (description.contains("$")) ? calendar.getLastDayOfMonth() : getMonthDay(description);
            pschedule = new MonthlyPSchedule(description, day, calendar);
            calendar.registerObserver(pschedule);
            return pschedule;
        }else if (type.equals("weekly")){
            pschedule = new WeeklyPSchedule(description, getWeekDay(description), getWeeksInterval(description), calendar);
            calendar.registerObserver(pschedule);
            return pschedule;
        }else {
            throw new InvalidParameterException("Invalid string format.");
        }
    }

    private String handleDefaultSchedule(String description){
        return (description.equals("monthly")) ? description + " $":
                (description.equals("weekly")) ? description + " 1 fri":
                        (description.equals("bi-weekly")) ? "weekly 2 fri": description;
    }

    private String getType(String description){
        int endIndex = description.indexOf(' ');
        return description.substring(0, endIndex).toLowerCase();
    }

    private int getMonthDay(String description){
        int startIndex = description.indexOf(' ') + 1;
        return Integer.parseInt(description.substring(startIndex));
    }

    private int getWeeksInterval(String description){
        int startIndex = description.indexOf(' ') + 1;
        int endIndex = description.substring(startIndex).indexOf(' ') + startIndex;
        return Integer.parseInt(description.substring(startIndex, endIndex));
    }

    private int getWeekDay(String description){
        int spaceIndex = description.indexOf(' ') + 1;
        spaceIndex += description.substring(spaceIndex).indexOf(' ');
        return calendar.weekDayIndex(description.substring(spaceIndex + 1));
    }

}
