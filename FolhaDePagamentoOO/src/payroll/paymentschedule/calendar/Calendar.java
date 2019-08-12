package payroll.paymentschedule.calendar;

import payroll.memento.Memento;
import payroll.memento.Originator;
import payroll.observers.Observer;
import payroll.observers.Subject;

import java.util.ArrayList;

public class Calendar implements Subject, Originator {
    private static final int[] monthsDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] weekDays = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturnday", "sunday"};
    private ArrayList observers = new ArrayList<Observer>();
    private int lastObserver = -1;
    private int today;
    private int weekDay;
    private int month;
    private static boolean started = false;

    public Calendar(int _today, String _weekDay, int _month){
        if (started) return;
        today = _today;
        weekDay = weekDayIndex(_weekDay);
        month = _month - 1;
        started = true;
    }

    public int getToday(){
        return today;
    }

    public int getMonth(){
        return month + 1;
    }

    public int getLastDayOfMonth(){
        return monthsDays[month];
    }

    public static int getLastDayOfMonth(int m){
        return monthsDays[m - 1];
    }

    public int getWeekDay(){
        return weekDay;
    }

    public static String[] getWeekDays(){
        return weekDays;
    }
    
    public boolean isUtilDay(int day){
        if (day == today) return isUtilDay(day, getMonth() + 1);
        int wd = weekDay, t = today, m = month;
        while (t != day){
            wd = (wd + 1) % 7;
            if (t + 1 <= monthsDays[m]){
                t++;
            }else{
                t = 1;
                m = (m + 1) % 12;
            }
        }
        return (wd < 5);
    }

    public boolean isUtilDay(int day, int mth){
        int wd = weekDay, t = today, m = month;
        mth = mth - 1;
        while (t != day || m != mth){
            wd = (wd + 1) % 7;
            if (t + 1 <= monthsDays[m]){
                t++;
            }else{
                t = 1;
                m = (m + 1) % 12;
            }
        }
        return (wd < 5);
    }

    public int weekDayIndex(String wd){
        for (int i = 0; i < weekDays.length; i++) {
            if (weekDays[i].contains(wd.toLowerCase())) return i;
        }
        return -1;
    }

    public int howMayDaysBeforeTheDay(int day){
        int wd = weekDay, t = today, m = month;
        int count = 0;
        while (t != day){
            wd = (wd + 1) % 7;
            if (t + 1 <= monthsDays[m]){
                t++;
            }else{
                t = 1;
                m = (m + 1) % 12;
            }
            count += 1;
        }
        return count;
    }

    public int sumDays(int n, int m){
        int lastDayNextMonth = getLastDayOfMonth(getMonth() + 1);
        int sum =  n + m;
        return (sum > lastDayNextMonth) ? sum - lastDayNextMonth : sum;
    }

    public void goToNextDay(){
        today = (today < monthsDays[month]) ? today + 1 : 1;
        month = (today == 1) ? (month + 1) % 12 : month;
        weekDay = (weekDay + 1) % 7;
        notifyObservers();
    }

    public void registerObserver(Observer o){
        observers.add(o);
    }

    public void removeObserver(Observer o){
        observers.remove(o);
    }

    public void notifyObservers(){
        for (int i = 0; i < observers.size(); i++) {
            Observer o = (Observer) observers.get(i);
            o.update();
        }
    }

    public Memento createMemento(){
        return new CalendarMemento(today, weekDay, month, (ArrayList) observers.clone());
    }

    public void setMemento(Memento mem){
        CalendarMemento m = (CalendarMemento) mem;
        today = m.getToday();
        weekDay = m.getWeekDay();
        month = m.getMonth();
        observers = m.getObservers();
    }

    public void printObservers(){
        Observer n;
        for (Object observer : observers) {
            n = (Observer) observer;
            n.print();
        }
    }
}