package payroll.paymentschedule;

import payroll.paymentschedule.calendar.Calendar;

public class MonthlyPSchedule extends PaymentSchedule{
    private int realPayDay;

    public MonthlyPSchedule(String description, int payDay, Calendar calendar){
        super(description, calendar);
        // so recebe o primeiro pagamento se trabalhar 15 dias até la
        realPayDay = -1;
        this.payDay = payDay;
        int today = calendar.getToday();
        if (calendar.howMayDaysBeforeTheDay(payDay) >= 15) {
            calcRealPayDay();
        }
    }

    @Override
    public boolean checkIftodayIsPayDay(){
        if (realPayDay != -1){
            return (calendar.getToday() == realPayDay);
        }
        return false;
    }

    @Override
    public int getPayDay(){
        return realPayDay;
    }

    @Override
    public int getPaymentFrequency(){
        return 4;
    }

    private void calcRealPayDay(){
        int lastDayOfMonth = calendar.getLastDayOfMonth();
        if (description.contains("$")){
            payDay = lastDayOfMonth;
        }
        int p = payDay;
        if (payDay > lastDayOfMonth) payDay = lastDayOfMonth;
        System.out.println();
        if (calendar.isUtilDay(payDay)){
            realPayDay = payDay;
        }else if (payDay <= lastDayOfMonth - 2){
            realPayDay = (calendar.isUtilDay(payDay + 1)) ? payDay + 1 : payDay + 2;
        }else if (payDay == lastDayOfMonth - 1){
            realPayDay = (calendar.isUtilDay(lastDayOfMonth)) ? lastDayOfMonth: 1;
        }else{
            realPayDay = (calendar.isUtilDay(1)) ? 1 : 2;
        }
        payDay = p;
    }

    @Override
    public void update() {
        if (calendar.getToday() == calendar.sumDays(payDay, 3)){
            calcRealPayDay();
        }
    }
}
