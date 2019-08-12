package payroll.payment;

import payroll.Cloneable;

public interface Payment extends Cloneable {
    String getDescription();
    void setValue(double value);
    double getValue();
    void setAttribute(String attr, double value);
    void setAttribute(String attr, String value);
    double getPaymentPercentage();
    void setPaymentPercentage(double paymentPercentage);
    void resetPayment();
    Payment getClone();
}
