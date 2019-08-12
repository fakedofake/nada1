package payroll.syndicate;

import java.util.ArrayList;

public class ConcreteSyndicate implements Syndicate{
    private static ArrayList<String> ids = new ArrayList<String>();
    private double syndicateTax;
    private ArrayList<Double> serviceTaxes;
    private String id;

    public ConcreteSyndicate(double syndicateTax){
        this.syndicateTax = syndicateTax;
        serviceTaxes = new ArrayList<Double>();
        this.id = "";
    }

    @Override
    public String getId(){
        return id;
    }

    @Override
    public void setId(String id){
        if (validateId(id)) {
            if (!this.id.equals("")) {
                ids.remove(this.id);
            }
            this.id = id;
            ids.add(id);
        }
    }

    @Override
    public boolean validateId(String id){
        String s;
        for (Object i : ids){
            s = (String) i;
            if (s.equals(id)) return false;
        }
        return true;
    }

    @Override
    public void addService(double service){
        serviceTaxes.add(service);
    }

    @Override
    public double getSyndicateTax(){
        return syndicateTax;
    }

    @Override
    public void setSyndicateTax(double syndicateTax) {
        this.syndicateTax = syndicateTax;
    }

    @Override
    public ArrayList getServiceTaxes(){
        return serviceTaxes;
    }

    @Override
    public void resetSyndicate() {
        serviceTaxes.clear();
    }

    @Override
    public ConcreteSyndicate getClone(){
        ConcreteSyndicate c = new ConcreteSyndicate(syndicateTax);
        c.setId(id);
        for (int i = 0; i < this.serviceTaxes.size(); i++) {
            c.addService(this.serviceTaxes.get(i));
        }
        return c;
    }
}
