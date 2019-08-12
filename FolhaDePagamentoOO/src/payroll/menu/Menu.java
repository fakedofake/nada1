package payroll.menu;

import payroll.utils.Validator;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    private ArrayList<String> options;

    Menu(ArrayList options){
        this.options = options;
        if (options.isEmpty()){
            throw new InvalidParameterException("Options cannot be empty");
        }
    }

    Menu(String[] options){
        this.options = new ArrayList<String>(Arrays.asList(options));
        if (this.options.isEmpty()){
            throw new InvalidParameterException("Options cannot be empty");
        }
    }

    public void displayOptions(){
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("[%d]  %s\n", i + 1, options.get(i));
        }
    }

    public static int getIntegerEntry(int start, int limit){
        Scanner input = new Scanner(System.in);
        int o;
        String asw;
        while (true) {
            asw = input.next();
            if (Validator.isInteger(asw)) {
                o = Integer.parseInt(asw);
                if (o >= start && o <= limit) {
                    return o;
                }
            }
            System.out.print("Invalid entry. Try again.\n::");
        }
    }

    public static int getIntegerEntry(int limit){
        return getIntegerEntry(1, limit);
    }

    public static boolean confirmation(){
        Scanner input = new Scanner(System.in);
        String asw;
        while (true){
            System.out.println("Are you sure? [y/n]");
            asw = input.next();
            if (asw.toLowerCase().charAt(0) == 'y'){
                return true;
            }else if (asw.toLowerCase().charAt(0) == 'n'){
                return false;
            }else{
                System.out.print("Invalid option. Try again.\n::");
            }
        }
    }

    public static double getDecimalEntry(){
        Scanner input = new Scanner(System.in);
        String asw;
        while (true) {
            asw = input.next();
            if (Validator.isNumeric(asw)) {
                return Double.parseDouble(asw);
            }
            System.out.print("Invalid entry. Try again.\n::");
        }
    }

    public static double getDecimalEntry(double limit){
        Scanner input = new Scanner(System.in);
        String asw;
        double n;
        while (true) {
            asw = input.next();
            if (Validator.isNumeric(asw)) {
                 n = Double.parseDouble(asw);
                 if (n <= limit) return n;
            }
            System.out.printf("Invalid entry. Limit: %f. Try again.\n::", limit);
        }
    }

    public static String getStringEntry(){
        Scanner input = new Scanner(System.in);
        return input.next().toLowerCase();
    }

    public static String getLineEntry(){
        Scanner input = new Scanner(System.in);
        return input.nextLine().toLowerCase();
    }

    public static String getSpecificEntry(String[] options){
        Scanner input = new Scanner(System.in);
        String asw;
        do {
            asw = input.next().toLowerCase();
            for (int i = 0; i < options.length; i++) {
                if (options[i].contains(asw)){
                    return asw;
                }
            }
            System.out.print("Invalid entry. Try again.\n::");
        }while (true);
    }
}
