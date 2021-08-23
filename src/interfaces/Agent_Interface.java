package interfaces;

import java.util.Scanner;
import java.sql.*;
import manager.IOManager;
import views.Agent_View;


    /**
     * Agent Interface
     */
    public class Agent_Interface { 
    /**
     * Command-line interface for agents
     * @param conn Connection object
     */
    public static void Agent(Connection conn) {
        Agent_View agent_view = new Agent_View();
        agent_view.prepare(conn);
       
        /**
         * Command-line interface for agent interface
         */
        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success; 
        boolean valid_start_date = true;
        boolean valid_end_date = true;
        while (true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("[1] Get All Customers Associated With a Particular Agent\n");
            System.out.println("[2] Identify Customers With Overdue Bills\n");
            System.out.println("[3] Customers With Pending Claims That Have Not Been Serviced Recently\n");
            System.out.println("[4] Compute the Estimated Revenue Generated by an Agent\n");
            System.out.println("[5] Update a Customer's Policy\n");
            System.out.println("[6] Get All Polcies Associated With a Particular Customer\n"); 
            System.out.println("[7] Get Policy Information Associated With a Customer's Policy\n"); 
            System.out.println("[8] Get All Customers (and Dependants) Associated With a Particular Policy\n"); 
            System.out.println("[9] Get All Adjusters an Agent Communicates With\n");
            System.out.println("[10] Disconnect!");
            System.out.println("--------------------------------------------------------------");
            System.out.print("\nSelect From the List of Options Above: ");
            boolean conditional = input.hasNextInt();
            if (conditional) {
                menue_selection = input.nextInt();
                if (menue_selection == 1) {
                    System.out.print("\nEnter 6-Digit Agent ID to Retrieve All Customers Managed By That Agent: ");
                    int agent_id = agent_view.user_integer();
                    success = agent_view.getAgentID(agent_id);
                    if (agent_id == success) {
                        agent_view.getAgentCustomers(agent_id);
                    }
                    else {
                        System.out.println("Agent With ID " + agent_id + " Does NOT Exist!\n");
                    }
                }
                else if (menue_selection == 2) {
                    System.out.println("Customers with Overdue Bills: ");
                    agent_view.getOverdueBill();
                }
                else if (menue_selection == 3) {
                    System.out.println("Customers with Pending Claims: ");
                    agent_view.getPendingClaim();
                }
                else if (menue_selection == 4) {
                    System.out.print("Revenue by the Agent: ");
                    int agent_id = agent_view.user_integer();
                    success = agent_view.getAgentID(agent_id);
                    if (agent_id == success) {
                        int estimated_revenue = agent_view.getEstimatedRevenue(agent_id);
                        System.out.println("Estimated revenue for agent " + agent_id + " is " + estimated_revenue + "\n");
                    }
                    else {
                        System.out.println("Agent With ID " + agent_id + " Does NOT Exist!");
                    }
                }
                else if (menue_selection == 5) {
                    int index;
                    int intPlaceHolder = 0;
                    String stringPlaceHolder = "";
                    System.out.print("Please Enter an Existing 6-digit Policy ID To Update the Policy: ");
                    int policy_id = agent_view.user_integer();
                    success = agent_view.getPolicyID(policy_id);
                    if (policy_id == success) {
                        System.out.println("\n--------------------------------------------------------------");
                        System.out.println("[1] Change Type Associated With Customer Policy");
                        System.out.println("[2] Change Cost Associated With Customer Policy");
                        System.out.println("[3] Change Coverage Associated With Customer Policy");
                        System.out.println("[4] Change Deductible Associated With Customer Policy");
                        System.out.println("[5] Change Coinsurance Associated With Customer Policy");
                        System.out.println("[6] Change Effective Date Associated With Customer Policy");
                        System.out.println("[7] Change Expiration Date Associated With Customer Policy");
                        System.out.println("[8] Change Policy Status Associated With Customer Policy");
                        System.out.println("--------------------------------------------------------------\n");
                        System.out.print("Please Choose What Part of the Policy You Would Like to Update: ");
                        menue_selection = input.nextInt();
                        if (menue_selection == 1) {
                            index = 1;
                            System.out.print("Update Type: [1] Single, [2] Family ");
                            String type = IOManager.policyType(1);
                            agent_view.updateCustomerPolicy(policy_id, intPlaceHolder, type, index);                            
                        }
                        else if (menue_selection == 2) {
                            index = 2;
                            System.out.print("Update Cost: ");
                            double cost = IOManager.intInputDouble(0.00, 999999.99);
                            agent_view.updateCustomerPolicy(policy_id, cost, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 3) {
                            index = 3;
                            System.out.print("Update Coverage: ");
                            double coverage = IOManager.intInputDouble(0, 999999.99);
                            agent_view.updateCustomerPolicy(policy_id, coverage, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 4) {
                            index = 4;
                            System.out.print("Update Deductible: ");
                            double deductible = IOManager.intInputDouble(0, 9999.99);
                            agent_view.updateCustomerPolicy(policy_id, deductible, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 5) {
                            index = 5;
                            System.out.print("Update Coinsurance: ");
                            double coinsurance = IOManager.intInputDouble(0, 99);
                            agent_view.updateCustomerPolicy(policy_id, coinsurance, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 6) {
                            index = 6;
                            System.out.print("Update Effective Date: ");
                            String effective_date = "";
                            while (valid_start_date) {
                                effective_date = input.next();
                                valid_start_date = agent_view.validateDate(effective_date);
                            } 
                            agent_view.updateCustomerPolicy(policy_id, intPlaceHolder, effective_date, index);
                        }
                        else if (menue_selection == 7) {
                            index = 7;
                            System.out.print("Update Expiration Date: ");
                            String expire_date = "";
                            while (valid_end_date) {
                                expire_date = input.next();
                                valid_end_date = agent_view.validateDate(expire_date);
                            } 
                            agent_view.updateCustomerPolicy(policy_id, intPlaceHolder, expire_date, index);
                        }
                        else if (menue_selection == 8) {
                            index = 8;
                            System.out.println("Update Policy Status: [1] Pending, [2] Active, [3] Inactive");
                            String policy_status = IOManager.policyStatus(1);
                            agent_view.updateCustomerPolicy(policy_id, intPlaceHolder, policy_status, index);
                        }
                        else {
                            System.out.println("Invalid Policy ID! Try Again!");
                        }
                    }
                    
                }
                else if (menue_selection == 6) {
                    System.out.print("Please Enter an Existing 6-digit Customer ID to Get All Policies Associated With the Customer: ");
                    int customer_id = agent_view.user_integer();
                    success = agent_view.getCustomerID(customer_id);
                    if (customer_id == success) {
                        agent_view.getAllCustomerPolicyList(customer_id);
                    }
                    else {
                        System.out.println("Customer With ID " + customer_id + " Does NOT Exist! Try Again!");
                    }                    
                }
                else if (menue_selection == 7) {
                    System.out.print("Please Enter an Existing 6-digit Policy ID to Get Policy Information Associated With That Specific Policy: ");
                    int policy_id = agent_view.user_integer();
                    success = agent_view.getPolicyID(policy_id);
                    if (policy_id == success) {
                        int success1 = agent_view.getPolicyInfo(policy_id);
                    }
                    else {
                        System.out.println("Policy With ID " + policy_id + " Does NOT Exist!");
                    }                
                }
                else if (menue_selection == 8) {
                    System.out.print("Enter Existing Policy ID To Retrieve Customers: ");
                    int policy_id = agent_view.user_integer();
                    success = agent_view.getPolicyID(policy_id);
                    if (policy_id == success) {
                        agent_view.getAllCustomersList(policy_id);
                    }
                    else {
                        System.out.println("Policy With ID " + policy_id + " Does NOT Exist!");
                    } 
                }
                else if (menue_selection == 9) {
                    System.out.print("Enter Existing Agent ID to Retrieve Adjusters They Communicate With: ");
                    int agent_id = agent_view.user_integer();
                    success = agent_view.getAgentID(agent_id);
                    if (agent_id == success) {
                        success = agent_view.getAdjusterAgent(agent_id);
                    }
                    else {
                        System.out.println("Agent With ID " + agent_id + " Does NOT Exist!");
                    } 
                }
                else if (menue_selection == 10) {
                    System.exit(0);
                }
            }
            else {
                System.out.println("Invalid Input! Try again!\n");
                input.nextLine();
            } 
        }
    }
}