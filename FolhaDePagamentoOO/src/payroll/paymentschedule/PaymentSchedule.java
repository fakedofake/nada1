package payroll.paymentschedule;

import payroll.observers.Observer;
import payroll.paymentschedule.calendar.Calendar;

public abstract class PaymentSchedule implements Observer {
    protected String description;
    protected int payDay;
    protected Calendar calendar;

    protected PaymentSchedule(String description, Calendar calendar){
        this.description = description;
        this.calendar = calendar;
    }

    public String getDescription(){
        return this.description;
    }

    public abstract boolean checkIftodayIsPayDay();

    public abstract int getPayDay();

    public abstract int getPaymentFrequency();

    public void print(){
        System.out.println(getDescription());
    }

}
