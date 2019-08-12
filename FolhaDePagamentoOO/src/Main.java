import payroll.company.Company;
import payroll.menu.MainMenu;
import payroll.paymentschedule.calendar.Calendar;
import payroll.menu.Menu;


public class Main {

    public static void main(String[] args){
        int today, month, limit;
        String weekday;
        System.out.print("Enter moth number (ex: 6): ");
        month = Menu.getIntegerEntry(12);
        limit = Calendar.getLastDayOfMonth(month);
        System.out.print("Enter today (ex: 21): ");
        today = Menu.getIntegerEntry(limit);
        System.out.print("Enter weekday (ex: tue): ");
        weekday = Menu.getSpecificEntry(Calendar.getWeekDays());
        Calendar calendar = new Calendar(today, weekday, month);
        MainMenu menu = new MainMenu(new Company(calendar));
        menu.mainMenu();
    }
}