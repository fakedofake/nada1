package payroll.company;

import payroll.memento.Memento;
import payroll.memento.Originator;

import java.util.ArrayList;
import java.util.Arrays;

import payroll.paymentschedule.calendar.Calendar;
import payroll.utils.Validator;

public class Company implements Originator {
    private ArrayList<Employee> employees;
    private ArrayList<String> paymentScheduleOptions;
    private final String[] pMethodOptions = {"deposit", "check in hands", "mail check"};
    private final String[] employeeTypes = {"salaried", "commissioned", "hourly"};
    private boolean[] avaliableIds;
    private Calendar calendar;

    public Company(Calendar calendar){
        this.calendar = calendar;
        this.employees = new ArrayList<Employee>();
        String[] psOptions = {"monthly", "weekly", "bi-weekly"};
        this.paymentScheduleOptions = new ArrayList<String>(Arrays.asList(psOptions));
        this.avaliableIds = new boolean[500];
    }

    public String[] getEmployeeTypes(){
        return employeeTypes;
    }

    public Calendar getCalendar(){
        return calendar;
    }

    public int getFreeId(){
        for (int i = 0; i < avaliableIds.length; i++) {
            if (!avaliableIds[i]){
                avaliableIds[i] = true;
                return i;
            }
        }
        return -1;
    }

    public boolean validateId(int id){
        return avaliableIds[id];
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    public Employee getEmployee(int id){
        Employee e;
        for (Object employee: employees) {
            e = (Employee) employee;

            if (e.getId() == id) return e;
        }
        return null;
    }

    public boolean removeEmployee(int id){
        Employee e;
        for (Object employee: employees){
            e = (Employee) employee;
            if (e.getId() == id){
                avaliableIds[id] = false;
                employees.remove(e);
                return true;
            }
        }
        return false;
    }

    public void addSale(int id, double value){
        Employee e = getEmployee(id);
        e.getPayment().setAttribute("addSale", value);
    }

    public ArrayList getPScheduleOptions(){
        return paymentScheduleOptions;
    }

    public String[] getPMethodOptions(){
        return pMethodOptions;
    }

    public boolean validatePSchedule(String pSchedule){
        if (pSchedule.contains("monthly")) {
            if ((pSchedule.charAt(7) == ' ')) {
                String day = pSchedule.substring(8);
                if (!Validator.isInteger(day)) return false;
                int d = Integer.parseInt(day);
                return (d <= 31 && d > 0);
            }else{
                return false;
            }
        } else if (pSchedule.contains("weekly")){
            if (pSchedule.charAt(6) == ' '){
                String half = pSchedule.substring(7);
                String weeks = half.substring(0, half.indexOf(' '));
                if (Validator.isInteger(weeks)){
                    int w = Integer.parseInt(weeks);
                    if (!(w > 0 && w <= 4)) return false;
                }else{
                    return false;
                }
                String day = half.substring(half.indexOf(' ') + 1);
                return (calendar.weekDayIndex(day) != -1);
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean addPSchedule(String pSchedule){
        if(!validatePSchedule(pSchedule)) return false;
        for (int i = 0; i < paymentScheduleOptions.size(); i++) {
            if (paymentScheduleOptions.get(i).equals(pSchedule)){
                return true;
            }
        }
        paymentScheduleOptions.add(pSchedule);
        return true;
    }

    public Memento createMemento(){
        ArrayList<Employee> es = new ArrayList<Employee>();
        for (int i = 0; i < this.employees.size(); i++) {
            es.add(this.employees.get(i).getClone());
        }
        return new CompanyMemento(es, (ArrayList) paymentScheduleOptions.clone(),
                                    avaliableIds.clone());
    }

    public void setMemento(Memento mem){
        CompanyMemento m = (CompanyMemento) mem;
        this.employees = m.getEmployees();
        this.paymentScheduleOptions = m.getPsOptions();
        this.avaliableIds = m.getAvaliableIds();
    }

    public ArrayList searchEmployee(String name){
        ArrayList<Employee> found = new ArrayList<Employee>();
        for (int i = 0; i < this.employees.size(); i++) {
            if (this.employees.get(i).getName().contains(name)){
                found.add(this.employees.get(i));
            }
        }
        return found;
    }

    public ArrayList runPayroll(){
        calendar.goToNextDay();
        ArrayList<Employee> l = new ArrayList<Employee>();
        Employee e = employees.get(0);
        for (int i = 0; i < this.employees.size(); i++) {
            e = this.employees.get(i);
            if (e.getPSchedule().checkIftodayIsPayDay()){
                l.add(e.getClone());
                e.getPayment().resetPayment();
                if (e.getSyndicate() != null){
                    e.getSyndicate().resetSyndicate();
                }
            }
        }
        return l;
    }
}
