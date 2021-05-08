# Database-System
Custom database system with JDBC API. 


CSE341

Tal Derei


## Github
```
https://github.com/TalDerei/Database-System.git
```

## Running The Executable
```
cd /tad222derei/tad222
java -jar tad222.jar

OR 

cd /tad222derei/tad222
java Application
```

## Database Design
The Country Garden insurance database takes a monolithic approach in it's contruction. Relational Sets were extensively used in association with Entity Sets in order to reduce the computational requirements of large 'Natural Join' operations. In that regard, querying a customer id, agent id, adjuster id, policy or claim ids were a function of either computing the natural join of multiple tables or using the 1:1 maping from a relationship set where a customer id has an associative customer_policy and customer_claim table for faster lookup times for example. So the point of relationship sets was to reduce the number, size, and frequency of natural join operations, especially as the database gets larger. The database was structured around the relationship where an adjuster manages a customer's policy, and that agent communicates directly with an adjuster who manages the policy claim (if it exists). Customers have one dedicated agent as their main contact, agents can communicate with multiple adjusters, and an adjuster can manage multiple claims. A claim can only be associated with one customer (and their dependants) and one policy at any given time. At the higher levels of management, a Corperate entitity rules over all the employees (agents and adjusters) and can use the corperate interface to construct performance and monetary reports. It's important to note that for now, the corperate interface was NOT implemented (but doing so is relatively trivial and is a function of JOINING various fields on multiple inter-related tables). Instead, only the Customer, Agent, and Adjuster interfaces were implemented. In regards to the payment system, policy payments and claim payments were handled indeendtly with respect to each other. Customers can make policy payments towards to their policy (which for the sake of this insurance company is quoted on an annual basis) either in the form of Debit or Credit payments. Adjusters can make claim payments in the form of Checks or ACH Transfers respectively.

## Code Logic and Layout
The database is encapsulated as a single object, and a single connection is being opened on that object (for each interface respectively). You can only connect to one interface at a time, and when that connection terminates, the resources will be returned to the database and the connection closed. Then that database object has multiple interfaces associated with it, and PreparedStatement are attached to that object as well. The `/countyGarden` directory includes a different java file for each interface, and an 'Application.java' file representing main. The `/design` directory contains the relational schema, ER diagram, and data_generation files in .sql and .pdf formats. And the `/tad222` directory contains the executable .jar file and the binary executables. It's important to note that the .class files in /tad222 are structured similiar to a /src directory, where Application.class is the top-level 'main' function in /tad222, and the other .class files (for the interfaces) are in the `/insurance` directory as a package. 

### Code Generation
SQL Data Generator in dbForge Studio for Oracle Server - Devart
https://www.devart.com/dbforge/oracle/studio/data-generator.html

## Interfaces 

### Customer Interface
The Customer Interface was designed to allow customers to create user profiles, add new insurance policies (home, auto, insurance, and life) to existing customer profiles, and add claims on existing policies. If a customer does not exist, a new insurance policy cannot be created. If a policy does not exist, a new claim cannot be created for that policy. One of the main contemplations for the customer interface was whether attributes like 'deductible' or 'cost' should be included in the policy table. Should a customer be expected to know the policy's deductible and/or cost when adding a policy to the database via the customer interface? On the other hand, contemplating whether a customer can only add their personal information, and only an agent can enter a customer policy into the database by interacting with the agent interface (in which case I would keep 'deductible' and 'cost' in the policy schema). Ultimately, it depends on how the database is structured and what functionality we associate with the different interfaces. The database implements the former design, allowing users to create policies that have to be potentially fixed (and approved) by agents. 

A customer can interface with the database with the following options:

```
[1] Create a New Customer Profile
[2] Add a Policy
[3] Drop a Policy
[4] Add a Claim to an Existing Policy
[5] Make a Policy Payment
[6] Get All Information on an Existing Policy
[7] Check Claim Status Associated With a Claim
[8] Add Dependant to Policy
[9] Add/Drop a Vehicle Associated with a Auto Insurance Policy
[10] Exit
```

### Agent Interface
The Agent Interface was designed to allow agents to reviews customer policies and update them as neccessary. Since agents SHOULD be able to do everything a customer can, I left out all of the functionality in the customer interface and instead highlighted the unique features dedicated to agents. Agents deal with everything to do with policies, such as getting information on a customer's specific polcy, updating their policy information, changing their policy status from 'pending' to 'active' or 'inactive', etc. In association, for every customer policy, and agent has a single adjuster they communicate with for that specific policy (as their main form of contact if a user decided to start a claim on a policy).

An agent can interface with the database with the following options:

```
[1] Get All Customers Associated With a Particular Agent\n");
[2] Identify Customers With Overdue Bills\n");
[3] Customers With Pending Claims That Have Not Been Serviced Recently\n");
[4] Compute the Estimated Revenue Generated by an Agent\n");
[5] Update a Customer's Policy\n");
[6] Get All Polcies Associated With a Particular Customer\n"); 
[7] Get Policy Information Associated With a Customer's Policy\n"); 
[8] Get All Customers (and Dependants) Associated With a Particular Policy\n"); 
[9] Get All Adjusters an Agent Communicates With\n");
[10] Exit
```

### Adjuster Interface
The Adjuster Interface was designed to allow adjusters to manage evrything to do with policies. Adjusters can update claims, review claims and either 'Deny' or 'Accept' them, change fields in the claim, make claim payments to customers (checks or ACH transfers), etc. In the way the database is currently designed, customer's can only submit top-level claims, but cannot add items to their claim. Only professional adjusters can add the items to a claim request, and then decide whether to accept or reject that request.

An adjuster can interface with the database with the following options:

```
[1] Get all customers associated with an adjusters
[2] Identify claims that have not been serviced recently
[3] Update a customer's claim
[4] Get all claims associated with a particular customer 
[5] Get claim information associated with a customer's claim
[6] Get all customers (and dependants) associated with a particular claim
[7] Get all agents an adjuster communicates with
[8] Get all claims an adjuster manages
[9] Assign external remediation firms or body shops to claims
[10] Add an item to an existing claim request
[11] Get All Items in a Claim
[12] Make a Claim Payment to a Particular Customer on a Policy
[13] Exit!
```

## Testing Patterns
Customers can create new profiles, policies, and claims on existing policies. Each generates a unique identifier that uniquely identifies that customer, policy, and claim (i.e. cannot have any customer/policy/claim with the same ID). The database currently prints out the unique identifier and prompts the user to write down their unique identifiers in order to use them later on. I could have easily (and just as simply) printed it out to the console everytime they log in. 

In order to test, use exsiting profiles comprised of customer_id, policy_id, claim_id fields found in the 'customer', 'policy', and 'claim' relations in the database. This is a good starting point before creating your own customer profile, policy and claim requests. 

## Assumptions
Throughout the development of the database, there were various assumptions made in regards to the design choices of different components. 

[1] Rather than implementing a uniform policy cost (with different tiers), I made the assumption that everyone's risk (again dependent on a multitide of fields for each policy type) will not be the same. Because of this, insurance premums will vary from person to person because insuruers make policyholders pay a permium that reflects their own personal levels of risk, in association with the type of policy they choose and its level of coverage. If I had not made this assumption, I would had to develope a complex algorithm to make all these policy costs conform to some algorithmic cost basis. But this is a database course, not an algorithms course, so I maintained this assumption. So rather than making every policy tier the same constant cost, or trying to figure out the policy cost as a function of all the possible permutations of policy types and their fields, instead a customer enters the policy cost as quoted by the agent. The agent then reviews the policy and either accepts or rejects the policy if all these fields check out. 

[2] In regards to policy costs and claim payouts, I internally restricted the ranges to Policy = $0 - 5000 and Claims to $0 - 999,999 in order to restrict the price variance. 

[3] In regards to the payment system, the price of a policy is quoted yearly, but customers pay monthly. As the database currently stands, there exists some logic mechanisms to detect when a user has missed a monthly payment, BUT it does not automatically cancel the policy if the user has missed a payment. This is an insurance database, not an application that has time-clock based logic capabilities. Never the less, if a user misses a payment, it's the responsibility of the agent to set the Policy Status from 'Active' to 'Inactive'. The policy has ‘expire_date’ and ‘effective_date’ fields that quotes a policy for a year, and records when a policy payment is LATE or ON TIME. The policy_payments relational has ‘date’ and ‘status’ fields. When user makes their monthly payment, the date will automatically be inserted when they made their payment. Then you treat effective_date as a starting point, and just increment that by the month every time user makes a payment. Then you can identify whether it’s on time or overdue based on whether payment_date > (expire_date - effective_date) / 12. For simplification, we can further assume that your first monthly payment is due on the same day of the month as your original policy start date. For example, if your car insurance policy started on April 12th, your monthly payments are due on the 12th of each month. Down the line, i'd like to change the policy payments uniformly to be due at the beggining of the month for all policy holders.

[4] I don't allow for partial payments, instead a policy must be payed in full for the following billing month, otherwise it's flagged as overdue. There would be some consequences such as late fees, which I don't currently handle.

[5] Policy renewals are non-existent in the database, instead you'll need to make a new policy when the policy expires.

[6] The most consequential assumption was with respect to allowing users to enter to their OWN policies and claims requests in the database. Rather than allowing a customer to physically walk into the shop, sit down with an agent, and allow the agent to quote the policy and enter the policy information on behalf of the customer, I took a different approach. Customers can make policy and claim 'ticket' requests that will then require the attention of an agent to look over the policy information they've supplied and either 'Accept', 'Reject'  the policy. The policies submitted by the customer will start in an initial 'Pending' State that an agent will later review and make a final decision on. We further assume that all the policy information supplied by the customers was quoted by the insurance company. Rather than making all of these assumptions, I could have circumvented this complexity and soley allowed agents to create policies on behalf of customers. But this setup allows customers to create policies, and agents to review them.

## Limitations
[1] Can only add dependants after a policy has been created. A customer will note the number of dependants associated with a specific insurance policy, and then has to individually add the dependants to the insurance policy.

[2] You can only ensure one home under a single home insurance policy. This is the conventional approach for home insurance policies, where each home has a seperate policy. 

[3] None of the major insurance companies will offer you a policy on a month-to-month basis, but you might have the option to cancel your policy before the six month term is finished and get a refund on the remainder of the term. The database doesn't currently support support policy refunds.

## Future Development
The Corperate Interface displays the message "THE CORPERATE INTERFACE IS DOWN FOR MAINTENENCE AS THE DEPARTMENT IS UNDERGOING A RESTRUCTUERING!". Instituting a corperate entity into the database that can generate reports on policy/claim statistics, terminate or hire new employees (agent or adjuster) from the company, raise salaries, etc. is trivial and expected to be coming soon! 
