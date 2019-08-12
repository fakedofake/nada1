package payroll.utils;

public class Validator {

    public static boolean isNumeric(String n) {
        try {
            Double.parseDouble(n);
        }catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String n) {
        if (isNumeric(n) && Integer.parseInt(n) == Float.parseFloat(n)){
            return true;
        }else {
            return false;
        }
    }

    public static boolean validateTimeString(String time){
        String hours, minutes;
        int separatorIndex = time.indexOf(':');
        if (separatorIndex == -1) return false;
        hours = time.substring(0,separatorIndex);
        minutes = time.substring(separatorIndex + 1, time.length());
        if (!Validator.isInteger(hours)){
            return (Integer.parseInt(hours) >= 0 && Integer.parseInt(hours) < 24);

        }
        if (!Validator.isInteger(minutes)){
            return (Integer.parseInt(minutes) >=0 &&
                    Integer.parseInt(minutes) < 60);
        }
        return true;
    }
}
