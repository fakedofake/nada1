package payroll.syndicate;

import java.util.ArrayList;

public class SyndicateMemento {
    private double syndicateTax;
    private ArrayList<Double> serviceTaxes;
    private String id;

    public SyndicateMemento(String id, double syndicateTax, ArrayList<Double> serviceTaxes){
        this.id = id;
        this.syndicateTax = syndicateTax;
        this.serviceTaxes = serviceTaxes;
    }

    public double getSyndicateTax() {
        return syndicateTax;
    }

    public ArrayList<Double> getServiceTaxes() {
        return serviceTaxes;
    }

    public String getId() {
        return id;
    }
}
