package payroll.payment;

public class HourlyPayment extends SalariedPayment{
    private double paymentPerHour;
    private String punchInTime;

    public HourlyPayment(String description, double paymentPerHour){
        super(description, 0);
        this.paymentPerHour = paymentPerHour;
        this.punchInTime = "";
    }

    @Override
    public double getValue(){
        return value;
    }

    @Override
    public void setValue(double value){
        setPaymentPerHour(value);
    }

    @Override
    public void setAttribute(String attr, double value){
        if (attr.equals("paymentPerHour")){
            setPaymentPerHour(value);
        }else if (attr.equals("workedHours")){
            calcHoursPayment(value);
        }else {
            super.setAttribute(attr, value);
        }
    }

    public void setAttribute(String attr, String value){
        if (attr.equals("punchCard")){
            if (punchInTime.equals("")){
                punchInTime = value;
            }else{
                calcHoursPayment(calcTime(punchInTime, value));
                punchInTime = "";
            }
        }
    }

    private int[] getTimeFromString(String time){
        String hours, minutes;
        int separatorIndex = time.indexOf(':');
        int[] t = new int[2];
        hours = time.substring(0,separatorIndex);
        minutes = time.substring(separatorIndex + 1, time.length());
        t[0] = Integer.parseInt(hours);
        t[1] = Integer.parseInt(minutes);
        return t;
    }

    private double calcTime(String sTime, String fTime){
        int[] sT, fT;
        int minutes, hours;
        sT = getTimeFromString(sTime);
        fT = getTimeFromString(fTime);
        minutes = (fT[1] >= sT[1]) ? fT[1] - sT[1] : (fT[1] + 60) - sT[1];
        hours = (fT[0] >= sT[0]) ? fT[0] - sT[0] : (fT[0] + 24) - sT[0];
        return hours + (minutes / 60.0);
    }

    @Override
    public void resetPayment(){
        value = 0;
    }

    public double getPaymentPerHour() {
        return paymentPerHour;
    }

    public void setPaymentPerHour(double paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }

    public String getPunchInTime(){
        return punchInTime;
    }

    public void setPunchInTime(String pTime){
        punchInTime = pTime;
    }

    private void calcHoursPayment(double workedHours) {
        double pph = paymentPerHour, total = 0;
        if (workedHours > 8){
            total += 8 * pph;
            workedHours -= 8;
            pph *= 1.5;
        }
        total += pph * workedHours;
        this.value += total;
    }

    @Override
    public HourlyPayment getClone(){
        HourlyPayment h = new HourlyPayment(description, paymentPerHour);
        h.setAttribute("value", value);
        h.setPunchInTime(punchInTime);
        return h;
    }
}
