package interfaces;

/**
 * Corporate Interface
 */
public class Corporate_Interface {
    
    /**
     * Command-line interface for corporate
     * @param database Connection object
     */
    public static void Corperate() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("[1] Generate report on revenue\n");
        System.out.println("[2] Generate report on claims paid\n");
        System.out.println("[3] Generate report on profits based on policy type\n");
        System.out.println("[4] Get salary of an employee (agent or adjuster)\n");
        System.out.println("[5] Give an employee (agent or adjuster) a raise\n");
        System.out.println("[6] Terminate an employee (agent or adjuster) from the company\n");
        System.out.println("[7] Add an agent to the company\n");
        System.out.println("[8] Add an adjuster to the company\n");
        System.out.println("[9] Exit!");
        System.out.println("--------------------------------------------------------------");
        System.out.println("\n");
        System.out.println("***************************************************************************************************");
        System.out.println("THE CORPERATE INTERFACE IS DOWN FOR MAINTENENCE AS THE DEPARTMENT IS UNDERGOING A RESTRUCTUERING!");
        System.out.println("***************************************************************************************************");
    }
}