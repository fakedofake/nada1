package payroll.syndicate;

import payroll.Cloneable;

import java.util.ArrayList;

public interface Syndicate extends Cloneable {
    String getId();
    void setId(String id);
    boolean validateId(String id);
    void addService(double tax);
    double getSyndicateTax();
    void setSyndicateTax(double tax);
    ArrayList getServiceTaxes();
    void resetSyndicate();
    Syndicate getClone();
}
