package payroll.payment;

import java.security.InvalidParameterException;

public class PaymentFactory {
    public Payment createPayment(String type, double... args){
        if (type.equals("salaried")){
            return new SalariedPayment(type, args[0]);
        }else if (type.equals("commissioned")){
            return new CommissionedPayment(type, args[0], args[1]);
        }else if (type.equals("hourly")){
            return new HourlyPayment(type, args[0]);
        }else{
            throw new InvalidParameterException("Unknow type \"" + type + "\".");
        }
    }

}
