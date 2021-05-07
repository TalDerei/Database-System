/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

package insurance;

import java.util.Scanner;
import java.util.Random;
import java.util.regex.Pattern;

public class IOManager {
    /**
     * idNumber generates randonly generates 6-digit IDs
     */
    public static int idNumber(int max) {
        Scanner input = new Scanner(System.in); 
        Random rand = new Random();
        int id_number = rand.nextInt(max);
        return id_number;
    }

    /**
     * stringInput checks string length associated with user input
     */
    public static String stringInput(int max) {
        Scanner input = new Scanner(System.in); 
        String string = user_string();
        if (string.length() > max) {
            System.out.println("Please Limit Input to " + max + " Characters!");
            return stringInput(max);
        }
        return string;
    }

    /**
     * stringInputWithoutNumbers takes in strings (without numbers) with max length of 'max'
     */
    public static String stringInputWithoutNumbers(int max) {
        Scanner input = new Scanner(System.in); 
        String string = user_string();
        if (!string.replaceAll("[0-9]", "").equals(string)){
            System.out.println("String Contains a Number. Try Again Without Any Digits!");
            return stringInputWithoutNumbers(max);
        }
        if (string.length() > max) {
            System.out.println("Please Limit Input to " + max + " Characters!");
            return stringInputWithoutNumbers(max);
        }
        return string;
    }

    /**
     * intInput checks the character length associated with integer inputs of specific 'size'
     */
    public static int intInput(int min, int max, int size) {
        Scanner input = new Scanner(System.in); 
        int number = user_integer(size);
        String number_string = String.valueOf(number);
        if (number_string.length() != size || ((number < min) || (number > max))) {
            System.out.println("Please Enter Exactly " + size + " Digits from " + min + " to " + max);
            return intInput(min, max, size);
        } 
        return number;
    }

    /**
     * intInputLong checks the character length associated with long inputs of specific 'size'
     */
    public static long intInputLong(long size) {
        Scanner input = new Scanner(System.in); 
        long number = user_long(size);
        String number_string = String.valueOf(number);
        if (number_string.length() != size) {
            System.out.println("Please Enter Exactly " + size + " Digits!");
            return intInputLong(size);
        } 
        return number;
    }

    /**
     * intInputRange checks the length associated with integer inputs in the range from 'min' to 'max' 
     */
    public static int intInputRange(int min, int max) {
        Scanner input = new Scanner(System.in); 
        int number = user_integer(max);
        if (number < min || number > max) {
            System.out.println("Please Enter Value Between " + min + " and " + max);
            return intInputRange(min, max);
        }
        return number;
    }

     /**
     * Input checking constraints on integer inputs
     */
    public static int user_integer(int min) {
        Scanner input = new Scanner(System.in);
            boolean conditional = true;
            while (conditional) {
                boolean condition = input.hasNextInt();
                if (condition) {
                    int user_input = input.nextInt();
                    conditional = false; 
                    return user_input; 
                }
                else {
                    System.out.println("Please Try Again! Input is Too Long!");
                    conditional = true;
                    input.next();
                }
            }
        return 0;
    }

    /**
     * Input checking constraints on long inputs
     */
    public static long user_long(long max) {
        Scanner input = new Scanner(System.in);
            boolean conditional = true;
            while (conditional) {
                boolean condition = input.hasNextLong();
                if (condition) {
                    long user_input = input.nextLong();
                    conditional = false; 
                    return user_input; 
                }
                else {
                    System.out.println("Please Try Again! Input is Too Long!");
                    conditional = true;
                    input.next();
                }
            }
        return 0;
    }

    /**
     * Input checking constrains on double inputs
     */
    public static double intInputDouble(double min, double max) {
        Scanner input = new Scanner(System.in); 
        double number = user_double(min, max);
        if (number < min || number > max) {
            System.out.println("Please Enter Value Between " + min + " and " + max);
            return intInputDouble(min, max);
        }
        return number;
    }
    
     /**
     * Input checking constraints on string input
     */
    public static String user_string() {
        Scanner input = new Scanner(System.in);
            boolean conditional = true;
            while (conditional) {
                boolean condition = input.hasNextInt();
                if (!condition) {
                    String user_input = input.nextLine();
                    conditional = false; 
                    return user_input; 
                }
                else {
                    System.out.println("Entered integer! Please enter a string and try again!");
                    conditional = true;
                    input.next();
                }
            }
        return "0";
    }

    /**
     * Input checking constraints on email  
     */
    public static String validEmail() {
        Scanner input = new Scanner(System.in);
        String email = user_string();
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(regex);
        if (email != null) {
            if (pat.matcher(email).matches() == true) {
                // System.out.println("valid email value is: " + pat.matcher(email).matches());
                return email;
            }
            else {
                // System.out.println("valid email value is: " + pat.matcher(email).matches());
                System.out.print("Not A Valid Email Address. Try Again!\n");
                return validEmail();
            }
        }
        return email;
    }

    /**
     * Input checking constraints on phone number  
     */
    public static String validPhoneNumber(int max) {
        Scanner input = new Scanner(System.in);
        long phone_number = user_long(max);
        String phone_number_convert = Long.toString(phone_number);
        String regex = "^\\d{10}$";
        Pattern pat = Pattern.compile(regex);
        if (phone_number_convert != null) {
            if (pat.matcher(phone_number_convert).matches() == true) {
                return phone_number_convert;
            }
            else {
                System.out.println("Invalid Phone Number! Try Again!");
                return validPhoneNumber(max);
            }
        }
        return phone_number_convert;
    }

    /**
     * Input checking constraints on home condition  
     */
    public static String homeCondition(int max) {
        String condition;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            condition = "Bad";
        }
        else if (condition_value == 2) {
                condition = "Poor";
        }
        else if (condition_value == 3) {
            condition = "Fair";
        }
        else if (condition_value == 4) {
            condition = "Good";
        }
        else if (condition_value == 5) {
            condition = "Excellent";
        }
        else {
            System.out.println("Enter [1] Bad, [2] Poor, [3] Fair, [4] Good, [5] Excellent");
            return homeCondition(max);
        }
        return condition;
    }

    /**
     * Input checking constraints on double inputs
     */
    public static double user_double(double min, double max) {
        Scanner input = new Scanner(System.in);
            boolean conditional = true;
            while (conditional) {
                boolean condition = input.hasNextDouble();
                if (condition) {
                    double user_input = input.nextDouble();
                    conditional = false; 
                    return user_input; 
                }
                else {
                    System.out.println("Please enter a value in the range of " + min + " to " + max);
                    conditional = true;
                    input.next();
                }
            }
        return 0;
    }

    /**
     * Input checking constraints on yes or no
     */
    public static String yesOrNo() {
        String string = "";
        Scanner input = new Scanner(System.in); 
        String user_string = user_string();
        if (user_string.equals("Y") || user_string.equals("y")) {
            string = "Y";
        }
        else if (user_string.equals("N") || user_string.equals("n")) {
            string = "N";
        }
        else {
            System.out.println("Enter [Y]es or [N]o!");
            return yesOrNo();
        }
        return string;
    }

    /**
     * Input checking constraints on plan category for policy type
     */
    public static String policyPlan(int menue_selection) {
        String type;
        if (menue_selection == 1) {
            type = "Home";
        }
        else if (menue_selection == 2) {
            type = "Auto";
        }
        else if (menue_selection == 3) {
            type = "Health";
        }
        else if (menue_selection == 4) {
            type = "Life";
        }
        else {
            System.out.println("Enter [1] Home, [2] Auto, [3] Health, [4] Life");
            return policyPlan(menue_selection);
        }
        return type;
    }

    /**
     * Input checking constraints on plan category for home insurance
     */
    public static String planCategoryHomeInsurance(int max) {
        String planCategory;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            planCategory = "Home";
        }
        else if (condition_value == 2) {
            planCategory = "Apartment";
        }
        else if (condition_value == 3) {
            planCategory = "Condo";
        }
        else if (condition_value == 4) {
            planCategory = "Townhouse";
        }
        else if (condition_value == 5) {
            planCategory = "Flat";
        }
        else if (condition_value == 6) {
            planCategory = "Mobil Home";
        }
        else {
            System.out.println("Enter [1] Home, [2] Apartment, [3] Condo, [4] Townhouse, [5] Flat, [6] Mobil Home");
            return planCategoryHomeInsurance(max);
        }
        return planCategory;
    }

    /**
     * Input checking constraints on plan category for policy type
     */
    public static String policyType(int max) {
        String type;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            type = "Single";
        }
        else if (condition_value == 2) {
            type = "Family";
        }
        else {
            System.out.println("Enter Policy Type: [1] Single, [2] Family");
            return policyType(condition_value);
        }
        return type;
    }

    /**
     * Input checking constraints on vin number for auto insurance
     */
    public static String validVin(int range) {
        Scanner input = new Scanner(System.in); 
        long number = user_long(range);
        String number_string = String.valueOf(number);
        if (number_string.length() != range) {
            System.out.println("Please enter exactly " + range + " digits!");
            return validVin(range);
        }
        return number_string;
    }

    /**
     * Input checking constraints on gender
     */
    public static String gender() {
        String string = "";
        Scanner input = new Scanner(System.in); 
        String user_string = user_string();
        if (user_string.equals("M") || user_string.equals("m")) {
            string = "M";
        }
        else if (user_string.equals("F") || user_string.equals("f")) {
            string = "F";
        }
        else {
            System.out.println("Enter [M]ale or [F]emale!");
            return gender();
        }
        return string;
    }

    /**
     * Input checking constraints on plan category for auto insurance
     */
    public static String planCategoryAutoInsurance(int max) {
        String planCategory;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            planCategory = "Collision";
        }
        else if (condition_value == 2) {
            planCategory = "Motorist";
        }
        else if (condition_value == 3) {
            planCategory = "Liability";
        }
        else if (condition_value == 4) {
            planCategory = "Comprehensive";
        }
        else if (condition_value == 5) {
            planCategory = "Personal Injury";
        }
        else {
            System.out.println("Enter [1] Collision, [2] Motorist, [3] Liability, [4] Comprehensive, [5] Personal Injury\n");
            return planCategoryAutoInsurance(max);
        }
        return planCategory;
    }

    /**
     * Input checking constraints on plan category for health insurance
     */
    public static String planCategoryHealthInsurance(int max) {
        String planCategory;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            planCategory = "EPO";
        }
        else if (condition_value == 2) {
            planCategory = "POS";
        }
        else if (condition_value == 3) {
            planCategory = "HDH";
        }
        else if (condition_value == 4) {
            planCategory = "HMO";
        }
        else if (condition_value == 5) {
            planCategory = "PPO";
        }
        else {
            System.out.println("Enter [1] EPO, [2] POS, [3] HDH, [4] HMO, [5] PPO");
            return planCategoryHealthInsurance(max);
        }
        return planCategory;
    }

    /**
     * Input checking constraints on plan category for life insurance
     */
    public static String planCategoryLifeInsurance(int max) {
        String planCategory;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            planCategory = "Whole Life";
        }
        else if (condition_value == 2) {
            planCategory = "Universal Life";
        }
        else if (condition_value == 3) {
            planCategory = "Term Life";
        }
        else {
            System.out.println("Enter [1] Whole Life, [2] Universal Life, [3] Term Life");
            return planCategoryLifeInsurance(max);
        }
        return planCategory;
    }

    /**
     * Input checking constraints on medical status
     */
    public static String medicalStatus(int max) {
        String planCategory;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            planCategory = "Healthy";
        }
        else if (condition_value == 2) {
            planCategory = "Not Healthy";
        }
        else {
            System.out.println("Enter [1] Healthy, [2] Not Healthy");
            return medicalStatus(max);
        }
        return planCategory;
    }

    /**
     * Input checking constraints on accidents
     */
    public static String accident(int max) {
        String planCategory;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            planCategory = "Wind and Hail";
        }
        else if (condition_value == 2) {
            planCategory = "Theft";
        }
        else if (condition_value == 3) {
            planCategory = "Car Wreck";
        }
        else if (condition_value == 4) {
            planCategory = "Health-Related";
        }
        else if (condition_value == 5) {
            planCategory = "Personal Injury";
        }
        else if (condition_value == 6) {
            planCategory = "Water Damage";
        }
        else if (condition_value == 7) {
            planCategory = "Fire Damage";
        }
        else {
            System.out.println("Enter [1] Wind and Hail, [2] Theft, [3] Car Wreck, [4] Health-Related, [5] Personal Injury, [6] ater Damage, [7] Fire Damage");
            return accident(max);
        }
        return planCategory;
    }

    /**
     * Input checking constraints on payment method types
     */
    public static String cardType(int max) {
        String planCategory;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            planCategory = "Visa";
        }
        else if (condition_value == 2) {
            planCategory = "Mastercard";
        }
        else if (condition_value == 3) {
            planCategory = "Amex";
        }
        else if (condition_value == 4) {
            planCategory = "Discover";
        }
        else if (condition_value == 5) {
            planCategory = "American Express";
        }
        else {
            System.out.println("Enter [1] Visa, [2] Mastercard, [3] Amex, [4] Discover, [5] American Express");
            return cardType(max);
        }
        return planCategory;
    }

    /**
     * Input checking constraints on banks
     */
    public static String bank(int max) {
        String planCategory;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            planCategory = "BofA";
        }
        else if (condition_value == 2) {
            planCategory = "Citigroup";
        }
        else if (condition_value == 3) {
            planCategory = "Chase";
        }
        else if (condition_value == 4) {
            planCategory = "Wells Fargo";
        }
        else {
            System.out.println("Enter [1] BofA, [2] Citigroup, [3] Chase, [4] Wells Fargo");
            return bank(max);
        }
        return planCategory;
    }

     /**
     * Input checking constraints on banks
     */
    public static String policyStatus(int max) {
        String planCategory;
        Scanner input = new Scanner(System.in);
        int condition_value = user_integer(max);
        if (condition_value == 1) {
            planCategory = "Pending";
        }
        else if (condition_value == 2) {
            planCategory = "Active";
        }
        else if (condition_value == 3) {
            planCategory = "Inactive";
        }
        else {
            System.out.println("Enter [1] Pending, [2] Active, [3] Inactive");
            return bank(max);
        }
        return planCategory;
    }
}
