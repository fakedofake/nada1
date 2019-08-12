package payroll.payment;

import java.util.ArrayList;

public class CommissionedPayment extends SalariedPayment{
    private double commissionRate;
    private final double maxCommissionRate = 0.5;
    private ArrayList<Double> periodSales;

    public CommissionedPayment(String description, double value, double commissionRate){
        super(description, value);
        setCommissionRate(commissionRate);
        this.periodSales = new ArrayList<Double>();
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        if (commissionRate > maxCommissionRate){
            throw new NumberFormatException("The value is too high. Maximum value: "
                                            + maxCommissionRate);
        }
        this.commissionRate = commissionRate;
    }

    @Override
    public double getValue(){
        return value * paymentPercentage + calcTotalCommission();
    }

    @Override
    public void setAttribute(String attr, double value){
        if (attr.equals("commissionRate")){
            commissionRate = value;
        }else if (attr.equals("addSale")){
            periodSales.add(value);
        }
        else{
            super.setAttribute(attr, value);
        }
    }

    private double calcTotalCommission(){
        double total = 0;
        for (Double c: periodSales) {
            total += c * commissionRate;
        }
        return total;
    }

    @Override
    public void resetPayment(){
        this.periodSales.clear();
    }

    @Override
    public CommissionedPayment getClone(){
        CommissionedPayment c = new CommissionedPayment(description, value, commissionRate);
        for (int i = 0; i < this.periodSales.size(); i++) {
            c.setAttribute("addSale", this.periodSales.get(i));
        }
        return c;
    }
}
