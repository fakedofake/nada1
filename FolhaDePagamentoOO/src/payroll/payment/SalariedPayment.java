package payroll.payment;

import java.security.InvalidParameterException;

public class SalariedPayment implements Payment{
    protected String description;
    protected double value;
    protected double paymentPercentage;

    public SalariedPayment(String description, double value){
        this.description = description;
        this.value = value;
        this.paymentPercentage = 1;
    }

    public SalariedPayment(String description, double value, double paymentPercentage){
        this.description = description;
        this.value = value;
        this.paymentPercentage = paymentPercentage;
    }

     @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getValue() {
        return value * paymentPercentage;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void setAttribute(String attr, double value){
         if (attr.equals("value")){
             value = value;
         }else if (attr.equals("paymentPercentage")) {
             setPaymentPercentage(value);
         }else {
             throw new InvalidParameterException("Unknown attribute \"" + attr + "\".");
         }
    }

    @Override
    public void setAttribute(String attr, String value){
        if (attr.equals("description")){
            this.description = value;
        }else{
            throw new InvalidParameterException("Unknown attribute \"" + attr + "\".");
        }
    }

    @Override
    public void setPaymentPercentage(double paymentPercentage) {
        this.paymentPercentage = paymentPercentage;
    }

    @Override
    public double getPaymentPercentage() {
        return paymentPercentage;
    }

    @Override
    public void resetPayment(){
    }

    @Override
    public SalariedPayment getClone(){
        return new SalariedPayment(description, value, paymentPercentage);
    }
}
