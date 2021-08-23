package views.customerViews;

import java.sql.*;
import manager.IOManager;
import objects.CustomerObjects.*;

public class AddDropVehicle {
    /**
     * Add/Drop a vehicle associated with a auto insurance policy
     */
    public static void add_drop_vehicle(Connection connection) {
        /**
         * Policy object
         */
        Policy policy = new Policy();

        System.out.println("[1] Add Additional Vehicle to Auto Insurance Policy");
        System.out.println("[2] Delete Vehicle From Policy");
        int menue_selection = Input.user_integer();
        if (menue_selection == 1) {
            System.out.print("Enter Existing Auto Insurance Policy: ");
            int policy_id = Input.user_integer();
            if (policy.getPolicyID(policy_id) == policy_id) {
                int vehicle_id = IOManager.idNumber(999999);
                String extra_vehicle = "Yes";
                System.out.print("Enter Year Built: ");
                int year = IOManager.intInput(1800, 2021, 4);
                System.out.print("Enter Make: ");
                String make = IOManager.stringInput(10);
                System.out.print("Enter Model: ");
                String model = IOManager.stringInput(10);
                System.out.print("Enter 17-Digit Vin Number: ");
                String vin = IOManager.validVin(17);
                System.out.print("Enter License Plate: ");
                String license_plate = IOManager.stringInput(7);
                System.out.print("Enter Total Mileage: ");
                int total_mileage = IOManager.intInputRange(0, 999999);
                System.out.print("Enter Total Annual Mileage: ");
                int annual_miles = IOManager.intInputRange(0, 99999);
                System.out.print("Enter Market Value: ");           
                int market_value = IOManager.intInputRange(1000, 10000000);
                int success_values = policy.insertAdditionalVehicle(policy_id, vehicle_id, extra_vehicle, year, make, model, vin, license_plate, total_mileage, annual_miles, market_value);
                if (success_values == 1) {
                    try{
                        connection.commit();
                        System.out.println("TRANSACTION SUCCEEDED!\n");
                    }
                    catch (SQLException exception) {
                        System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                    }
                }
            }
        }
        else if (menue_selection == 2) {
            System.out.print("[2] Enter Exising Auto Insuance Policy: ");
            int vehicle_id = Input.user_integer();
            if (policy.deleteAdditionalVehicle(vehicle_id)) {
                try{
                    connection.commit();
                    System.out.println("TRANSACTION SUCCEEDED!\n");
                }
                catch (SQLException exception) {
                    System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                }
            }
        }
    }
}
