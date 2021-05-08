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
java -jar countryGarden.jar
```

## Design
The Country Garden insurance database takes a monolithic approach in it's contruction. Relational Sets were extensively used in association with Entity Sets in order to reduce the computational requires of large 'Natural Join' operations. In that regard, querying a customer id, agent id, adjuster id, policy or claim ids were a function of either computing the natural join of multiple tables or using the 1:1 maping of a relational table where a customer id has an associative customer_policy and customer_claim table for faster lookup times for example. So the point of relationship sets was to reduce the number, size, and frequency of natural join operations. The database was structured around the relationship where an adjuster manages a customer's policy, and that agent communicates directly with an adjuster who manages the policy claim (if there exists one). Customers have a one dedicated agent as their main contact, agents can communicate with multiple adjusters, and an adjuster can manage multiple claims. A claim can only be associated with one customer (and their dependants) and one policy at any given time. At the higher levels of management, a Corperate entitity rules over all the employees (agents and adjusters) and can use the relational schema to construct performance and monetary reports. It's important to note that for now, the corperate interface was NOT implemented (but doing so is relatively trivial and is a function of JOINING various fields on multiple inter-related tables). Instead, only the Customer, Agent, and Adjuster interfaces were implemented for now. In regards to the payment system, policy payments and claim payments were handled isolated with respect to each other. Customers can make policy payments towards to their policy (which for the sake of this insurance company is quoted on an annual basis) either in the form of Debit or Credit payments. Adjusters can make claim payments in the form of Checks or ACH Transfers respectively.

## Code Logic
The database is encapsulated as a single object, and a single connection is being opened on that object (for each interface respectively). You can only connect to one interface at a time, and when that connection terminates, the resources will be returned to the database and the connection closed. Then that database object has multiple interfaces associated with it, and PreparedStatement are attached to that object as well. The /src directory includes a different file for every interface, and an 'Application' file representing main. The /design directory contains the relational schema, ER diagram, and data_generation files as .sql and .pdf files. 

## Assumptions
Throughout the development of the database, there were various assumptions made in regards to the design choices of different components. 

[1] Rather than implementing a uniform policy cost (with different tiers) for all the possible permutations of policy costs associated with the number of dependants, the policy type, what the policy is insuring, etc. I made the assumption that everyone's risk (again depending on the multitide of fields for each policy type) will NOT be the same. Because of this, insurance premums will vary from person to person because insuruers try to make that each policyholders pays a permium that reflect their own person levels of risk with regards to the type of policy they choose and it's level of coverage. If I had not made this assumption, I would had to develope a complex algorithm to make all these policy features to some cost basis. But this is a database course, not an algorithms course, so I maintained this assumption.

[2] In regards to policy costs and claim payouts, I internally restricted the ranges to Policy = 0 - 5000 and Claims to 0 - 999,999 in order to restrict the distribution of costs.

[3] In regards to the payment system, the price of a policy is quoted yearly, but customers pay monthly. As the database currently stands, there exists some logic mechanisms to detect when a user has missed a monthly payment, BUT it does not automatically cancel the policy if the user has missed a payment. This is an insurance database, not an application that has time-clock based logic capabilities. Never the less, if a user misses a payment, it's the responsibility of the agent to set the Policy Status from 'Active' to 'Inactive'. The policy has ‘expire_date’ and ‘effective_date’ fields that quotes a policy for a year, and records when a policy payment is LATE or ON TIME. The policy_payments relational has ‘date’ and ‘status’ fields. When user makes their monthly payment, the date will automatically be inserted when they made their payment. Then you treat effective_date as a starting point, and just increment that by he month every time user makes a payment. Then you can identify whether it’s on time or overdue based on whether payment_date > (expire_date - effective_date) / 12. For simplification, we can further assume that your first monthly payment is due on the same day of the month as your original policy start date. For example, if your car insurance policy started on April 12th, your monthly payments are due on the 12th of each month. Down the line, i'd like to change the policy payments uniformly to be due at the beggining of the month for all policy holders.

[4] I don't allow for partial payments instead a policy must be payed in full for the following billing month, otherwise it's flagged as overdue. There would be some consequences such as late fees, which I don't currently handle.

[5] Policy renewals are non-existent in the database, instead you'll need to make a new policy when the policy expires.

[6] The most consequential assumption was with respect to allowing users to enter to their OWN policies and claims requests in the database. Rather than allowing a customer to physically walk into the shop, sit down with an agent, and allow the agent to quote the policy and enter the policy information on behalf of the customer, I took a different approach. Customers can make policy and claim 'ticket' requests that will then require the attention of the agent their assigned to look over the policy information they've supplied and either 'Accept', 'Reject'  the policy. The policies submitted by the customer will start in an initial 'Pending' State that an agent will later review and make a final decision on. We further assume that all the policy information supplied by the customers was quoted by the insurance company. Rather than making all of these assumptions, I could have circumvented this complexity and soley allowed agents to create policies on behalf of customers. This assumption allows customers to create policies, and agents to review them.

## Limitations
[1] Can only add dependants dependants after a policy has been created. A user will note the number of dependants associated with a specific insurance policy, and then has to add the dependants to the insurance policy.

[2] You can only ensure one home under a single home insurance policy. This is the conventional approach for home insurance policies, where each home has a seperate policy. 

[3] None of the major insurance companies will offer you a policy on a month-to-month basis, but you might have the option to cancel your policy before the six month term is finished and get a refund on the remainder of the term. The database doesn't currently support support policy refunds.

## Future Development
Opening the Corperate Interface displays the message "THE CORPERATE INTERFACE IS DOWN FOR MAINTENENCE AS THE DEPARTMENT IS UNDERGOING A RESTRUCTUERING!". Instituting a corperate entity into the database that can Generate report on claims and policies, terminate or hire new employee (agent or adjuster) from the company, raise salaries, etc. is trivial and expected to be coming soon!
