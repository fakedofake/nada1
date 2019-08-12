package payroll.company;

import payroll.payment.Payment;
import payroll.paymentschedule.PaymentSchedule;
import payroll.syndicate.Syndicate;

import java.util.ArrayList;

public class Employee implements Cloneable{
    private int id;
    private String name;
    private String address;
    private Payment payment;
    private Syndicate syndicate;
    private PaymentSchedule pSchedule;
    private String paymentMehod = "deposit";

    public Employee(int id, String name, String address, Payment payment, Syndicate syndicate,
                    PaymentSchedule pSchedule){
        this.id = id;
        this.name = name;
        this.address = address;
        this.payment = payment;
        this.payment.setPaymentPercentage(pSchedule.getPaymentFrequency()/4.0);
        this.syndicate = syndicate;
        this.pSchedule = pSchedule;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPaymentValue() {
        double total = payment.getValue();
        if (syndicate != null){
            total -= syndicate.getSyndicateTax();
            ArrayList taxes = (ArrayList) syndicate.getServiceTaxes();
            for (int i = 0; i < taxes.size(); i++) {
                total -= (double) taxes.get(i);
            }
        }
        return total;
    }

    public Payment getPayment(){
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Syndicate getSyndicate() {
        return syndicate;
    }

    public void setSyndicate(Syndicate syndicate) {
        this.syndicate = syndicate;
    }

    public void leaveSyndicate(){
        this.syndicate = null;
    }

    public PaymentSchedule getPSchedule() {
        return pSchedule;
    }

    public void setPSchedule(PaymentSchedule pSchedule) {
        this.pSchedule = pSchedule;
        double p = pSchedule.getPaymentFrequency()/4.0;
       payment.setPaymentPercentage(p);
    }

    public String getType(){
        return payment.getDescription();
    }

    public String getPaymentMethod(){
        return paymentMehod;
    }

    public void setPaymentMehod(String pMethod){
        this.paymentMehod = pMethod;
    }

    public Employee getClone(){
        Syndicate s = (syndicate == null) ? null : syndicate.getClone();
        return new Employee(id, name, address, payment.getClone(), s, pSchedule);
    }
}
