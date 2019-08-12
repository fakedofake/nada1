package payroll.menu;

import payroll.company.Company;
import payroll.company.Employee;
import payroll.memento.CareTaker;
import payroll.memento.ConcreteCareTaker;
import payroll.payment.PaymentFactory;
import payroll.paymentschedule.PScheduleFactory;
import payroll.paymentschedule.PaymentSchedule;
import payroll.syndicate.ConcreteSyndicate;
import payroll.utils.Validator;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EmptyStackException;

public class MainMenu{
    private Company company;
    private CareTaker calendarCaretaker;
    private CareTaker companyCareTaker;
    private boolean lastActionWasPayroll = false;

    public MainMenu(Company company){
        this.company = company;
        calendarCaretaker = new ConcreteCareTaker(company.getCalendar());
        companyCareTaker = new ConcreteCareTaker(company);
    }

    public void mainMenu(){
        String[] options = {"Search employee","Add employee", "Remove employee", "Alter employee",
                            "Punch card", "Add sale", "Add service tax", "Run payroll", "Undo", "Redo",
                            "Create payment schedule", "Exit"};
        Menu m = new Menu(options);
        do {
            System.out.println("\n============ Main menu ===============");
            m.displayOptions();
            System.out.print("\n:: ");
            int o = m.getIntegerEntry(options.length);
            switch (o) {
                case 1:
                    searchEmployeeByName();
                    break;
                case 2:
                    addEmployee();
                    break;
                case 3:
                    removeEmployee();
                    break;
                case 4:
                    alterEmployee();
                    break;
                case 5:
                    punchCard();
                    break;
                case 6:
                    addSale();
                    break;
                case 7:
                    addServiceTax();
                    break;
                case 8:
                    runPayroll();
                    break;
                case 9:
                    undo();
                    break;
                case 10:
                    redo();
                    break;
                case 11:
                    createPSchedule();
                    break;
                case 12:
                    System.exit(0);
            }
        }while(true);
    }

    private int getId(){
        System.out.print("id: ");
        return Menu.getIntegerEntry(0, 499);
    }

    private boolean validateId(int id){
        if(!company.validateId(id)){
            System.out.println("Invalid id");
            return false;
        }else{
            return true;
        }
    }

    private String getName(){
        System.out.print("Name: ");
        return Menu.getLineEntry();
    }

    private String getAddress(){
        System.out.print("Address: ");
        return Menu.getLineEntry();
    }

    private String getType(){
        String[] types = company.getEmployeeTypes();
        Menu m = new Menu(types);
        System.out.println("===== Type ====");
        m.displayOptions();
        System.out.print("\n:: ");
        int asw = Menu.getIntegerEntry(types.length);
        return types[asw - 1];
    }

    private double[] getSalariedPaymentInfo(){
        double[] info = new double[1];
        System.out.print("Payment value: ");
        info[0] = Menu.getDecimalEntry();
        return info;
    }

    private double[] getCommissionedPaymentInfo(){
        double[] info = new double[2];
        System.out.print("Payment value: ");
        info[0] = Menu.getDecimalEntry();
        System.out.print("Commission rate: ");
        info[1] = Menu.getDecimalEntry(0.5);
        return info;
    }

    private double[] getHourlyPaymentInfo(){
        double[] info = new double[1];
        System.out.print("Payment per hour: ");
        info[0] = Menu.getDecimalEntry();
        return info;
    }

    private double[] paymentInfo(String type){
        if (type.equals("salaried")){
            return getSalariedPaymentInfo();
        }else if (type.equals("commissioned")){
            return getCommissionedPaymentInfo();
        }else if (type.equals("hourly")){
            return getHourlyPaymentInfo();
        }else{
            throw new InvalidParameterException("Unkonw type \"" + type + "\".");
        }
    }

    private PaymentSchedule pScheduleByType(String type){
        PScheduleFactory psFactory = new PScheduleFactory(company.getCalendar());
        if (type.equals("salaried")){
            return psFactory.createPaymentSchedule("monthly $");
        }else if (type.equals("commissioned")){
            return psFactory.createPaymentSchedule("weekly 2 fri");
        }else {
            return psFactory.createPaymentSchedule("weekly 1 fri");
        }
    }

    private void addEmployee(){
        doNormalBackup();
        String name, address, type;
        PaymentFactory pFactory = new PaymentFactory();
        PaymentSchedule pSchedule;
        name = getName();
        address = getAddress();
        type = getType();
        double[] pInfo = paymentInfo(type);
        company.addEmployee(new Employee(company.getFreeId(),name,  address,
                                        pFactory.createPayment(type, pInfo), null,
                                        pScheduleByType(type)));
    }

    private void searchEmployeeByName(){
        String name = getName();
        ArrayList employees = (ArrayList) company.searchEmployee(name);
        if (employees.isEmpty()){
            System.out.println("Employee not found.");
            return;
        }
        printEmployees(employees);
    }

    private void printEmployees(ArrayList employees){
        Employee e;
        for (int i = 0; i < employees.size(); i++) {
            e = (Employee) employees.get(i);
            System.out.printf("Id: %d | Name: %s | Type: %s | Salary: %.2f | ", e.getId(),
                              e.getName(), e.getType(), e.getPaymentValue());
            System.out.printf("Payment method: %s | Payment schedule: %s\n", e.getPaymentMethod(),
                              e.getPSchedule().getDescription());
        }
    }

    private void removeEmployee(){
        doNormalBackup();
        int id = getId();
        if (!validateId(id)) return;
        company.removeEmployee(id);
    }

    private void punchCard(){
        int id = getId();
        if (!validateId(id)) return;
        Employee e = company.getEmployee(id);
        if (!e.getType().equals("hourly")){
            System.out.println("This employee isn't hourly.");
            return;
        }
        doNormalBackup();
        String time;
        do{
            System.out.print("Time (hh:mm): ");
            time = Menu.getStringEntry();
            if (Validator.validateTimeString(time)) {
                break;
            }else{
                System.out.println("Invalid time. Try again.\n::");
            }
        }while (true);
        e.getPayment().setAttribute("punchCard", time);
    }

    private void addSale(){
        int id = getId();
        if (!validateId(id)) return;
        if (!company.getEmployee(id).getType().equals("commissioned")){
            System.out.println("This employee isn't commissioned.");
            return;
        }
        doNormalBackup();
        System.out.print("Value: ");
        double value = Menu.getDecimalEntry();
        company.addSale(id, value);
    }

    private void addServiceTax(){
        int id = getId();
        if (!validateId(id)) return;
        Employee e = company.getEmployee(id);
        if (e.getSyndicate() == null){
            System.out.println("This employee doesn't belong to the syndicate.");
            return;
        }
        doNormalBackup();
        System.out.print("Tax value: ");
        e.getSyndicate().addService(Menu.getDecimalEntry());
    }

    private void alterEmployee(){
        int id = getId();
        if (!validateId(id)) return;
        Employee e = company.getEmployee(id);
        String[] options = {"Name", "Address", "Type", "Payment method", "Syndicate",
                            "Syndicate id", "Syndicate tax", "Payment schedule", "Exit"};
        Menu m = new Menu(options);
        int asw;
        do {
            System.out.println("======== Alter employee ==========");
            System.out.println(":: ");
            m.displayOptions();
            asw = Menu.getIntegerEntry(options.length);
            switch (asw){
                case 1:
                    doNormalBackup();
                    e.setName(getName());
                    break;
                case 2:
                    doNormalBackup();
                    e.setAddress(getAddress());
                    break;
                case 3:
                    doNormalBackup();
                    String type = getType();
                    double[] pInfo = paymentInfo(type);
                    PaymentFactory pFactory = new PaymentFactory();
                    e.setPayment(pFactory.createPayment(type, pInfo));
                    break;
                case 4:
                    doNormalBackup();
                    pmMenu(e);
                    break;
                case 5:
                    if (e.getSyndicate() == null){
                        System.out.println("Join syndicate.");
                        if (Menu.confirmation()){
                            doNormalBackup();
                            e.setSyndicate(new ConcreteSyndicate(5));
                        }
                    }else{
                        System.out.println("Leave syndicate.");
                        if (Menu.confirmation()){
                            doNormalBackup();
                            e.setSyndicate(null);
                        }
                    }
                    break;
                case 6:
                    if (e.getSyndicate() == null){
                        System.out.println("This employee doesn't belong to the syndicate.");
                        break;
                    }else{
                        System.out.print("Syndicate id: ");
                        String i = Menu.getStringEntry();
                        if (e.getSyndicate().validateId(i)){
                            doNormalBackup();
                            e.getSyndicate().setId(i);
                        }
                    }
                    break;
                case 7:
                    if (e.getSyndicate() == null){
                        System.out.println("This employee doesn't belong to the syndicate.");
                        break;
                    }else{
                        doNormalBackup();
                        e.getSyndicate().setSyndicateTax(Menu.getDecimalEntry());
                    }
                    break;
                case 8:
                    doNormalBackup();
                    psMenu(e);
                    break;
                case 9:
                    return;
            }
        } while (true);
    }

    private void psMenu(Employee e){
        PScheduleFactory psFactory = new PScheduleFactory(company.getCalendar());
        ArrayList<String> psOptions = (ArrayList) company.getPScheduleOptions();
        Menu m = new Menu(psOptions);
        System.out.println("========= Payment schedules =========");
        m.displayOptions();
        int o = Menu.getIntegerEntry(psOptions.size()) - 1;
        e.setPSchedule(psFactory.createPaymentSchedule(psOptions.get(o)));
    }

    private void pmMenu(Employee e){
        String[] pmOptions = company.getPMethodOptions();
        Menu m = new Menu(pmOptions);
        System.out.println("======== Payment method ========");
        m.displayOptions();
        int i = Menu.getIntegerEntry(pmOptions.length);
        e.setPaymentMehod(pmOptions[i - 1]);
    }

    private void runPayroll() {
        companyCareTaker.saveState();
        calendarCaretaker.saveState();
        lastActionWasPayroll = true;
        ArrayList l = (ArrayList) company.runPayroll();
        Employee e;
        System.out.printf("Data: %d/%d\n", company.getCalendar().getToday(),
                           company.getCalendar().getMonth());
        System.out.println("============== Paid today ==============");
        if (!l.isEmpty()){
            printEmployees(l);
        }
    }

    private void doNormalBackup(){
        companyCareTaker.saveState();
        lastActionWasPayroll = false;
    }

    private void undo(){
        try{
            if (lastActionWasPayroll){
                calendarCaretaker.undo();
            }
            companyCareTaker.undo();
        }catch (EmptyStackException exception){
            System.out.println("This is already the oldest change.");
        }
    }

    private void redo(){
        try{
            companyCareTaker.redo();
            if (lastActionWasPayroll){
                calendarCaretaker.redo();
            }
        }catch (EmptyStackException exception){
            System.out.println("This is already the most recent change.");
        }
    }

    private void createPSchedule(){
        doNormalBackup();
        System.out.print("New payment schedule: ");
        String pSchedule = Menu.getLineEntry();
        if (!company.addPSchedule(pSchedule)){
            if (pSchedule.contains("weekly")){
                System.out.println("Invalid. Please note that the maximum interval is 4 weeks)");
            }else{
                System.out.println("invalid format.");
            }
        }
    }
}
