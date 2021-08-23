package interfaces;

import java.sql.*;
import java.util.Scanner;

import manager.DBManager;
import views.customerViews.*;

import java.time.LocalDate;
import java.util.Calendar;
/**
 * Customer Interface
 */
public class Customer_Interface {
    static int menue_selection;
    static int success_value = 0;
    static boolean valid_start_date = true;
    static boolean valid_end_date = true;
    static String date_of_birth = "";
    static String effective_date = "";
    static String expire_date = "";

    /**
     * Command-line interface for customers
     * @param connection Connection object
     */
    public static void Customer(Connection connection) {
        int menue_selection;
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("[1] Create a New Customer Profile\n");
            System.out.println("[2] Add a Policy\n");
            System.out.println("[3] Drop a Policy\n");
            System.out.println("[4] Add a Claim to an Existing Policy\n");
            System.out.println("[5] Make a Policy Payment\n");
            System.out.println("[6] Get All Information on an Existing Policy\n");
            System.out.println("[7] Check Claim Status Associated With a Claim\n");
            System.out.println("[8] Add Dependant to Policy\n");
            System.out.println("[9] Add/Drop a Vehicle Associated with a Auto Insurance Policy\n");
            System.out.println("[10] Disconnect!\n");
            System.out.println("--------------------------------------------------------------");

            System.out.print("\nSelect From the List of Options Above: ");
            boolean conditional = input.hasNextInt();
            if (conditional) {
                menue_selection = input.nextInt();
                switch(menue_selection) {
                    case 1: 
                        CreateProfile.create_profile(connection);
                        break;
                    case 2: 
                        AddPolicy.add_policy(connection);
                        break;
                    case 3: 
                        DropPolicy.drop_policy(connection);
                        break;
                    case 4: 
                        AddClaim.add_claim(connection);
                        break;
                    case 5: 
                        MakePolicyPayment.make_policy_payment(connection);
                        break;
                    case 6: 
                        GetPolicyInformation.get_policy_information(connection);
                        break;
                    case 7: 
                        CheckClaimStatus.check_claim_status(connection);
                        break;
                    case 8: 
                        AddDependant.add_dependant(connection);
                        break;
                    case 9: 
                        AddDropVehicle.add_drop_vehicle(connection);
                        break;
                    case 10: 
                        DBManager.disconnect();
                    default:
                        Customer_Interface.Customer(connection);
                        break;
                }
            }
        }
    }
}
