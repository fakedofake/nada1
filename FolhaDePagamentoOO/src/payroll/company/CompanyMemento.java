package payroll.company;

import payroll.memento.Memento;

import java.util.ArrayList;

public class CompanyMemento implements Memento {
    private ArrayList employees;
    private ArrayList psOptions;
    private boolean[] avaliableIds;

    CompanyMemento(ArrayList employees, ArrayList psOptions, boolean[] avaliableIds){
        this.employees = employees;
        this.psOptions = psOptions;
        this.avaliableIds = avaliableIds;
    }

    public ArrayList getEmployees() {
        return employees;
    }

    public ArrayList getPsOptions() {
        return psOptions;
    }

    public boolean[] getAvaliableIds() {
        return avaliableIds;
    }
}
