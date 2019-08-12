package payroll.paymentschedule;

import payroll.paymentschedule.calendar.Calendar;

public class WeeklyPSchedule extends PaymentSchedule {
    private int weekDay;
    private int weeksBetween;

    public WeeklyPSchedule(String description, int _weekDay, int _interval, Calendar calendar){
        super(description, calendar);
        weekDay = _weekDay;
        weeksBetween = _interval;
        calcFirstPayDay();
    }

    @Override
    public boolean checkIftodayIsPayDay() {
        return calendar.getToday() == payDay;
    }

    @Override
    public int getPayDay() {
        return payDay;
    }

    @Override
    public int getPaymentFrequency(){
        return weeksBetween;
    }

    private void calcPayDay(){
        int lastDayOfmonth = calendar.getLastDayOfMonth();
        payDay += (7 * weeksBetween);
        if (payDay > lastDayOfmonth) {
            payDay -= lastDayOfmonth;
        }
    }

    private void calcFirstPayDay(){
        int today = calendar.getToday();
        int _weekDay = calendar.getWeekDay();
        int intervalOfDays = weekDay - _weekDay;
        if (intervalOfDays > 2){
            payDay = today + intervalOfDays + (7 * (weeksBetween - 1));
        }else if (intervalOfDays < -2){
            payDay = today + intervalOfDays + (7 * (weeksBetween + 1));
        }else{
            payDay = today + intervalOfDays + (7 * weeksBetween);
        }
        int lastDayOfMonth = calendar.getLastDayOfMonth();
        if (payDay > lastDayOfMonth){
            payDay -= lastDayOfMonth;
        }
    }

    @Override
    public void update() {
        if (calendar.getToday() == payDay + 1){
            calcPayDay();
        }
    }
}
