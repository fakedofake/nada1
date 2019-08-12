package payroll.paymentschedule.calendar;

import payroll.memento.Memento;

import payroll.observers.Observer;

import java.util.ArrayList;

public class CalendarMemento implements Memento {
    private int today;
    private int weekDay;
    private int month;
    private ArrayList observers;

    CalendarMemento(int today, int weekDay, int month, ArrayList observers){
        this.today = today;
        this.weekDay = weekDay;
        this.month = month;
        this.observers = observers;
    }

    public int getToday() {
        return today;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public int getMonth() {
        return month;
    }

    public ArrayList getObservers(){
        return observers;
    }
}
