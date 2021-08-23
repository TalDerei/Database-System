package objects.CustomerObjects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;

import manager.DBManager;

public class Policy {
    /**
     * Prepared Statements
     */
    public static PreparedStatement getPolicyInformation;
    private static PreparedStatement addPolicy; 
    private static PreparedStatement dropPolicy;
    private static PreparedStatement checkPolicyID;
    private static PreparedStatement dropAdditionalVehicle;
    private static PreparedStatement addHomeInsurance;
    private static PreparedStatement addAutoInsurance;
    private static PreparedStatement addHealthInsurance;
    private static PreparedStatement addLifeInsurance;
    private static PreparedStatement checkPolicyCost;
    private static PreparedStatement addAdditionalVehicle;
    private static PreparedStatement checkPolicyDate;
    private static PreparedStatement addDependant;

    /**
     * Generic policy parameters 
     */
    private int customer_id;
    private int policy_id;
    private String type;
    private double cost;
    private double coverage;
    private double deductible;
    private int coinsurance;
    private String effective_date;
    private String expire_date;
    private String plan;
    private String policy_status;

    /**
     * Home Insurance parameters
     */
    private String city;
    private String state;
    private int zip_code;
    private String address;
    private int year_built;
    private String condition;
    private double square_foot;
    private double lot_size;
    private int credit_score;
    private double mortgage_payment;
    private double market_value;
    private String personal_property_replacement;
    private String plan_category;

    /**
     * Life Insurance paramatrers
     */
    private String tobacco_use;
    private String occupation;
    private String medical_status;
    private String family_medical_history;
    private String beneficiary_name;
    private int beneficiary_social_security;

    /**
     * Auto Insurance parameters
     */
    private String make;
    private String model;
    private String vin;
    private String license_plate;
    private String driver_license;
    private int total_mileage;
    private int annual_miles;
    private String date_of_birth;
    private String gender;
    private String traffic_violations;
    private int number_of_dependants;

    /**
     * Health Insurance parameters
     */
    private Double out_of_pocket_maximum;
    private String pre_existing_conditions;
    private Double estimated_copay;

    /**
     * Constructor for dropping existing Policy object
     */
    public Policy() {
    }

    /**
     * Constructor to create a Policy object
     * @param policy_id
     * @param type
     * @param cost
     * @param coverage
     * @param deductible
     * @param coinsurance
     * @param effective_date
     * @param expire_date
     * @param plan
     * @param policy_status
     */
    public Policy(int customer_id, int policy_id, String type, double cost, double coverage, double deductible, int coinsurance, String effective_date, String expire_date, String plan, String policy_status) {
        this.customer_id = customer_id;
        this.policy_id = policy_id;
        this.type = type;
        this.cost = cost;
        this.coverage = coverage;
        this.deductible = deductible;
        this.coinsurance = coinsurance;
        this.effective_date = effective_date;
        this.expire_date = expire_date;
        this.plan = plan;
        this.policy_status = policy_status;
    }   

    /**
     * Return all information about a customer's existing policy 
     * @param policy_id
     * @return Policy information
     */
    public int getPolicyInfo(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.getPolicyInformation = conn.prepareStatement("SELECT * FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
        int success = 0;
        try {
            getPolicyInformation.setInt(1, policy_id);
            ResultSet resultset = getPolicyInformation.executeQuery();
            while (resultset.next()) {
                int customer_id = resultset.getInt("customer_id");
                String type = resultset.getString("type");
                int cost = resultset.getInt("cost");
                int coverage = resultset.getInt("coverage");
                int deductible = resultset.getInt("deductible");
                int coinsurance = resultset.getInt("coinsurance");
                String effective_date = resultset.getString("effective_date");
                String expire_date = resultset.getString("expire_date");
                String plan = resultset.getString("plan");
                System.out.println("policy customer_id is: " + customer_id);
                System.out.println("policy policy_id is: " + policy_id);
                System.out.println("policy type is: " + type);
                System.out.println("policy cost is: " + cost);
                System.out.println("policy coverage is: " + coverage);
                System.out.println("policy deductible is: " + deductible);
                System.out.println("policy coinsurance is: " + coinsurance);
                System.out.println("policy effective_date is: " + effective_date);
                System.out.println("policy expire_date is: " + expire_date);
                System.out.println("policy plan is: " + plan);
                success = 1;
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Policy ID Invalid! No Policy Exists!");
        }
        return success;
    }

    /**
     * Check for existing customer policy ID
     * @param policy_id
     * @return Policy ID
     */
    public int getPolicyID(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.checkPolicyID = conn.prepareStatement("SELECT policy_id FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int id = 0;
        try {
            checkPolicyID.setInt(1, policy_id);
            ResultSet resultset = checkPolicyID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("policy_id");
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Policy ID Invalid! No Policy Exists!");
        }
        return id;
    }

    /**
     * Drop policy object from the database
     * @param policy_id 
     * @return Whether drop was successful
     */
    public Boolean dropPolicy(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.dropPolicy = conn.prepareStatement("DELETE FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        try {
            dropPolicy.setInt(1, policy_id);
            dropPolicy.executeUpdate();
            return true;
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return false;
    }

    /**
     * Insert poolicy object
     * @return Whether inserting generic policy is successful
     */
    public Boolean insertGenericPolicy() {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addPolicy = conn.prepareStatement("INSERT INTO policy (CUSTOMER_ID, POLICY_ID, TYPE, COST, COVERAGE, DEDUCTIBLE, COINSURANCE, EFFECTIVE_DATE, EXPIRE_DATE, PLAN, POLICY_STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        try {
            addPolicy.setInt(1, customer_id);
            addPolicy.setInt(2, policy_id);
            addPolicy.setString(3, type);
            addPolicy.setDouble(4, cost);
            addPolicy.setDouble(5, coverage);
            addPolicy.setDouble(6, deductible);
            addPolicy.setInt(7, coinsurance);
            addPolicy.setDate(8, Date.valueOf(effective_date));
            addPolicy.setDate(9, Date.valueOf(expire_date));
            addPolicy.setString(10, plan);
            addPolicy.setString(11, policy_status);
            addPolicy.executeUpdate();
            return true;
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return false;
    }

    /**
     * Deletes additional vehicles from existing auto insurance policy 
     * @param vehicle_id
     * @return Whether deleting vehicle from insurance policy is successful
     */
    public Boolean deleteAdditionalVehicle(int vehicle_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.dropAdditionalVehicle = conn.prepareStatement("DELETE FROM vehicle WHERE vehicle_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        try {
            dropPolicy.setInt(1, vehicle_id);
            dropAdditionalVehicle.executeUpdate();
            System.out.println("\nSuccessfully Deleted Additional Vehicle From Auto Insurance Policy!\n");
            return true;
        }
        catch (SQLException exception) {
            System.out.println("Failed to Delete Additional Vehicle From Auto Insurance! Try Again!");
        }
        return false;
    }

    /**
     * Insert home insurance policy object
     * @return Whether inserting home insurance is successful
     */
    public Boolean insertHomeInsurance() {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addHomeInsurance = conn.prepareStatement("INSERT INTO home_insurance (POLICY_ID, CITY, STATE, ZIP_CODE, ADDRESS, YEAR_BUILT, CONDITION, SQUARE_FOOT, LOT_SIZE, CREDIT_SCORE, MORTGAGE_PAYMENT, MARKET_VALUE, PERSONAL_PROPERTY_REPLACEMENT, PLAN_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        try {
            addHomeInsurance.setInt(1, policy_id);
            addHomeInsurance.setString(2, city);
            addHomeInsurance.setString(3, state);
            addHomeInsurance.setInt(4, zip_code);
            addHomeInsurance.setString(5, address);
            addHomeInsurance.setInt(6, year_built);
            addHomeInsurance.setString(7, condition);
            addHomeInsurance.setDouble(8, square_foot);
            addHomeInsurance.setDouble(9, lot_size);
            addHomeInsurance.setInt(10, credit_score);
            addHomeInsurance.setDouble(11, mortgage_payment);
            addHomeInsurance.setDouble(12, market_value);
            addHomeInsurance.setString(13, personal_property_replacement);
            addHomeInsurance.setString(14, plan_category);
            addHomeInsurance.executeUpdate();
            return true;
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Home Insurance to Database! Something Went Wrong, Try Again!");
        }
        return false;
    }

    /**
     * Insert home auto insurancepolicy object
     * @return Whether inserting auto insurance is successful
     */
    public Boolean insertAutoInsurance() {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addAutoInsurance = conn.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, DRIVER_LICENSE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE, AGE, GENDER, CREDIT_SCORE, NUMBER_OF_DEPENDANTS, TRAFFIC_VIOLATIONS, STATE, PLAN_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        try {
            addAutoInsurance.setInt(1, policy_id);
            addAutoInsurance.setInt(2, year_built);
            addAutoInsurance.setString(3, make);
            addAutoInsurance.setString(4, model);
            addAutoInsurance.setString(5, vin);
            addAutoInsurance.setString(6, license_plate);
            addAutoInsurance.setString(7, driver_license);
            addAutoInsurance.setInt(8, total_mileage);
            addAutoInsurance.setInt(9, annual_miles);
            addAutoInsurance.setDouble(10, market_value);
            addAutoInsurance.setDate(11, Date.valueOf(date_of_birth));
            addAutoInsurance.setString(12, gender);
            addAutoInsurance.setInt(13, credit_score);
            addAutoInsurance.setInt(14, number_of_dependants);
            addAutoInsurance.setString(15, traffic_violations);
            addAutoInsurance.setString(16, state);
            addAutoInsurance.setString(17, plan_category);
            addAutoInsurance.executeUpdate();
            return true;
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Auto Insurance to Database! Something Went Wrong, Try Again!");
        }
        return false;
    }

    /**
     * Insert health insurance policy object
     * @return Whether inserting health insurance is successful
     */
    public Boolean insertHealthInsurance() {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addHealthInsurance = conn.prepareStatement("INSERT INTO health_insurance (POLICY_ID, PLAN_CATEGORY, OUT_OF_POCKET_MAXIMUM, TOBACCO_USE, AGE, PRE_EXISTING_CONDITIONS, NUMBER_OF_DEPENDANTS, ESTIMATED_COPAY) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
        try {
            addHealthInsurance.setInt(1, policy_id);
            addHealthInsurance.setString(2, plan_category);
            addHealthInsurance.setDouble(3, out_of_pocket_maximum);
            addHealthInsurance.setString(4, tobacco_use);
            addHealthInsurance.setDate(5, Date.valueOf(age));
            addHealthInsurance.setString(6, pre_existing_conditions);
            addHealthInsurance.setInt(7, number_of_dependants);
            addHealthInsurance.setDouble(8, estimated_copay);
            addHealthInsurance.executeUpdate();
            return true;
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Health Insurance to Database! Something Went Wrong, Try Again!");
        }
        return false;
    }

    /**
     * Insert life insurance policy object
     * @return Whether inserting life insurance is successful
     */
    public Boolean insertLifeInsurance() {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addLifeInsurance = conn.prepareStatement("INSERT INTO life_insurance (POLICY_ID, PLAN_CATEGORY, AGE, GENDER, TOBACCO_USE, OCCUPATION, MEDICAL_STATUS, FAMILY_MEDICAL_HISTORY, BENEFICIARY_NAME, BENEFICIARY_SOCIAL_SECURITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        try {
            addLifeInsurance.setInt(1, policy_id);
            addLifeInsurance.setString(2, plan_category);
            addLifeInsurance.setDate(3, Date.valueOf(age));
            addLifeInsurance.setString(4, gender);
            addLifeInsurance.setString(5, tobacco_use);
            addLifeInsurance.setString(6, occupation);
            addLifeInsurance.setString(7, medical_status);
            addLifeInsurance.setString(8, family_medical_history);
            addLifeInsurance.setString(9, beneficiary_name);
            addLifeInsurance.setInt(10, beneficiary_social_security);
            addLifeInsurance.executeUpdate();
            return true;
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Life Insurance to Database! Something Went Wrong, Try Again!");
        }
        return false;
    }

    /**
     * Checks policy cost associated with a customer's policy
     * @param policy_id
     * @return Policy cost
     */
    public int getPolicyCost(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.checkPolicyCost = conn.prepareStatement("SELECT cost FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int cost = 0;
        try {
            checkPolicyCost.setInt(1, policy_id);
            ResultSet resultset = checkPolicyCost.executeQuery();
            while (resultset.next()) {
                cost = resultset.getInt("cost");
                System.out.println("policy cost is: " + cost);
            }
            try {
                resultset.close();
            }
            catch (SQLException exception) {
                System.out.println("Cannot close resultset!");
            }
        }
        catch (SQLException exception) {
            System.out.println("Failed to Retrieve Policy Cost! Try Again!");
        }
        return cost;
    }

    /**
     * Retrieve the current date based on internal system clock 
     * @return Internal system clock time
     */
    public java.sql.Date currentDate() {
        java.util.Date current = new java.util.Date();
        return new java.sql.Date(current.getTime());
    }

    /**
     * Insert additional vehicles into existing auto insurance policy 
     * @param policy_id
     * @param vehicle_id
     * @param extra_vehicle
     * @param year
     * @param make
     * @param model
     * @param vin
     * @param license_plate
     * @param total_mileage
     * @param annual_miles
     * @param market_value
     * @return Whether inserting additional vehicles to existing policy is successful
     */
    public int insertAdditionalVehicle(int policy_id, int vehicle_id, String extra_vehicle, int year, String make, String model, String vin, String license_plate, int total_mileage, int annual_miles, int market_value) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addAdditionalVehicle = conn.prepareStatement("INSERT INTO vehicle (POLICY_ID, VEHICLE_ID, EXTRA_VEHICLE, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
        int success = 0;
        try {
            addAdditionalVehicle.setInt(1, policy_id);
            addAdditionalVehicle.setInt(2, vehicle_id);
            addAdditionalVehicle.setString(3, extra_vehicle);
            addAdditionalVehicle.setInt(4, year);
            addAdditionalVehicle.setString(5, make);
            addAdditionalVehicle.setString(6, model);
            addAdditionalVehicle.setString(7, vin);
            addAdditionalVehicle.setString(8, license_plate);
            addAdditionalVehicle.setInt(9, total_mileage);
            addAdditionalVehicle.setInt(10, annual_miles);
            addAdditionalVehicle.setInt(11, market_value);
            success = addAdditionalVehicle.executeUpdate();
            System.out.println("\nSuccessfully Added Additional Vehicle to Auto Insurance Policy!\n");
        }
        catch (SQLException exception) {
            System.out.println("Failed to Insert Additional Vehicle to Auto Insurance! Try Again!");
        }
        return success;
    }

    /**
     * Check date associated with customer policy to determine policy payment is on time or overdue
     * @param policy_id
     * @return Date
     */
    public Date[] getPolicyDate(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.checkPolicyDate = conn.prepareStatement("SELECT effective_date, expire_date FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
                
        Date[] dates = new Date[2];
        Date effective_date;
        Date expire_date;
        try {
            checkPolicyDate.setInt(1, policy_id);
            ResultSet resultset = checkPolicyDate.executeQuery();
            while (resultset.next()) {
                effective_date = resultset.getDate("effective_date");
                System.out.println("effective_date is: " + effective_date);
                expire_date = resultset.getDate("expire_date");
                System.out.println("expire_date is: " + expire_date);
                dates[0] = effective_date;
                dates[1] = expire_date;
            }
            try {
                resultset.close();
            }
            catch (SQLException exception) {
                System.out.println("Cannot close resultset!");
            }
        }
        catch (SQLException exception) {
            System.out.println("Failed to Retrive Policy Date! Try Again!");
        }
        return dates;
    }

    /**
     * Insert new dependant associated with a customer policy
     * @param dependant_id
     * @param name
     * @param social_security
     * @param date_of_birth
     * @return Whether inserting additional dependants was successful
     */
    public Boolean insertDependant(int dependant_id, int policy_id, String name, int social_security, String date_of_birth) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addDependant = conn.prepareStatement("INSERT INTO dependant (DEPENDANT_ID, POLICY_ID, NAME, SOCIAL_SECURITY, DATE_OF_BIRTH) VALUES (?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
        try {
            addDependant.setInt(1, dependant_id);
            addDependant.setInt(2, policy_id);
            addDependant.setString(3, name);
            addDependant.setInt(4, social_security);
            addDependant.setDate(5, Date.valueOf(date_of_birth));
            addDependant.executeUpdate();
            return true;
        }
        catch (SQLException exception) {
            System.out.println("Failed to Insert Dependant! Try Again!");
        }
        return false;
    }

    /**
     * Getters and Setter functions
     */
    public void setCustomerId(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setPolicyId(int policy_id) {
        this.policy_id = policy_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    public void setDeductible(double deductible) {
        this.deductible = deductible;
    }

    public void setCoinsurance(int coinsurance) {
        this.coinsurance = coinsurance;
    }

    public void setEffectiveDate(String effective_date) {
        this.effective_date = effective_date;
    }

    public void setExpireDate(String expire_date) {
        this.expire_date = expire_date;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setPolicyStatus(String policy_status) {
        this.policy_status = policy_status;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(int zip_code) {
        this.zip_code = zip_code;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setYearBuilt(int year_built) {
        this.year_built = year_built;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setSquareFootage(double square_foot) {
        this.square_foot = square_foot;
    }

    public void setLotSize(double lot_size) {
        this.lot_size = lot_size;
    }

    public void setMortagePayment(double mortgage_payment) {
        this.mortgage_payment = mortgage_payment;
    }

    public void setMarketValue(double market_value) {
        this.market_value = market_value;
    }

    public void setPersonalPropertyReplacement(String personal_property_replacement) {
        this.personal_property_replacement = personal_property_replacement;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setLicensePlate(String license_plate) {
        this.license_plate = license_plate;
    }

    public void setDriverLicense(String driver_license) {
        this.driver_license = driver_license;
    }

    public void setTotalMileage(int total_mileage) {
        this.total_mileage = total_mileage;
    }

    public void setAnnualMiles(int annual_miles) {
        this.annual_miles = annual_miles;
    }

    public void setMarketValue(int market_value) {
        this.market_value = market_value;
    }

    public void setDateofBirth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCreditScore(int credit_score) {
        this.credit_score = credit_score;
    }

    public void setTrafficViolatations(String traffic_violations) {
        this.traffic_violations = traffic_violations;
    }

    public void SetDependants(int number_of_dependants) {
        this.number_of_dependants = number_of_dependants;
    }
    
    public void setPlanCategory(String plan_category) {
        this.plan_category = plan_category;
    }

    public void setTobaccoUse(String tobacco_use) {
        this.tobacco_use = tobacco_use;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setMedicalStatus(String medical_status) {
        this.medical_status = medical_status;
    }

    public void setFamilyMedicalHistory(String family_medical_history) {
        this.family_medical_history = family_medical_history;
    }
    
    public void setBeneficiaryName(String beneficiary_name) {
        this.beneficiary_name = beneficiary_name;
    }

    public void setBeneficiarySocial(int beneficiary_social_security) {
        this.beneficiary_social_security = beneficiary_social_security;
    }

    public void setOutOfPocketMax(Double out_of_pocket_maximum) {
        this.out_of_pocket_maximum = out_of_pocket_maximum;
    }

    public void setPreExistingConditions(String pre_existing_conditions) {
        this.pre_existing_conditions = pre_existing_conditions;
    }

    public void setEstimatedCopay(Double estimated_copay) {
        this.estimated_copay = estimated_copay;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public int getPolicyId() {
        return policy_id;
    }

    public String getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    public double getCoverage() {
        return coverage;
    }

    public double getDeductible() {
        return deductible;
    }

    public int getCoinsurance() {
        return coinsurance;
    }

    public String getEffectiveDate() {
        return effective_date;
    }

    public String getExpireDate() {
        return expire_date;
    }

    public String getPlan() {
        return plan;
    }

    public String getPolicyStatus() {
        return policy_status;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZipCode() {
        return zip_code;
    }

    public String getAddress() {
        return address;
    }

    public int getYearBuilt() {
        return year_built;
    }

    public String getCondition() {
        return condition;
    }

    public double getSquareFootage() {
        return square_foot;
    }

    public double getLotSize () {
        return lot_size;
    }

    public int getCreditScore() {
        return credit_score;
    }

    public double getMortgagePayment() {
        return mortgage_payment;
    }

    public String getPersonalPropertyReplacement() {
        return personal_property_replacement;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVin() {
        return vin;
    }

    public String getLicensePlate() {
        return license_plate;
    }

    public String getDriversLicense() {
        return driver_license;
    }

    public int getTotalMileage() {
        return total_mileage;
    }

    public int getAnnualMiles() {
        return annual_miles;
    }

    public double getMarketValue() {
        return market_value;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public int getCreditHistory() {
        return credit_score;
    }

    public String getTrafficViolatations() {
        return traffic_violations;
    }

    public int getDependants() {
        return number_of_dependants;
    }
    
    public String getPlanCategory() {
        return plan_category;
    }

    public String getTobaccoUse() {
        return tobacco_use;
    }
    
    public String getOccupation() {
        return occupation;
    }

    public String getMedicalStatus() {
        return medical_status;
    }

    public String getFamilyMedicalHistory() {
        return family_medical_history;
    }

    public String getBeneficiaryName() {
        return beneficiary_name;
    }

    public int getBeneficiarySocial() {
        return beneficiary_social_security;
    }

    public Double getOutOfPocketMax() {
        return out_of_pocket_maximum;
    }

    public String getPreExistingConditions() {
        return pre_existing_conditions;
    }

    public Double getEstimatedCopay() {
        return estimated_copay;
    }
}
