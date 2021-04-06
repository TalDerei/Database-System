create table employee(
    employee_id varchar(5),
    name varchar(20) not null,
    primary key (employee_id));

create table customer(
    customer_id varchar(5),
    name varchar(20) not null,
    address varchar(20),
    age varchar(2),
    primary key (customer_id));
    
create table agent(
    agent_id varchar(5),
    primary key (agent_id),
    foreign key (agent_id) references employee(employee_id) on delete set null);

create table adjuster(
    adjuster_id varchar(5),
    primary key (adjuster_id),
    foreign key (adjuster_id) references employee(employee_id) on delete set null);

create table policy(
    policy_id varchar(5), 
    customer_id varchar(5),
    policy_type varchar(20),
    policy_cost numeric(8,2),
    coverage varchar(10),
    primary key (policy_id),
    foreign key (customer_id) references customer(customer_id) on delete set null);

create table home_insurance(
    policy_id varchar(5),
    address varchar(20),
    geographic_location varchar(20),
    year_built numeric (4,0),
    condition varchar(10),
    lot_size numeric (10,2),
    credit_history numeric (3,0),
    mortgage_payment numeric (4,0),
    market_value numeric (8,0),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table auto_insurance(
    policy_id varchar(5),
    year numeric (4,0),
    make varchar(10),
    model varchar(10),
    vin_number varchar(17),
    market_value numeric (10,2),
    age varchar (2),
    gender varchar (4),
    geographic_location varchar(20),
    credit_history numeric (3,0),
    driving_record varchar(10),
    annual_miles numeric (6,0),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table health_insurance(
    policy_id varchar(5),
    age varchar (2),
    geographic_location varchar(20),
    tobacco_use varchar (5),
    plan_category varchar (5),
    copay numeric (8,0),
    out_of_pocket_maximum numeric (8,0),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table claim(
    claim_id varchar(5), 
    customer_id varchar(5),
    claim_type varchar(20),
    claim_payment varchar (5),
    description varchar(40),
    primary key (claim_id),
    foreign key (customer_id) references customer(customer_id) on delete set null);

create table company(
    name varchar(20),
    type varchar(20), 
    conatct_info varchar(20),
    primary key (name));
    
create table item(
    item_id varchar(5), 
    claim_id varchar(5),
    policy_id varchar(5),
    item_type varchar(20),
    description varchar(20),
    primary key (item_id),
    foreign key (claim_id) references claim(claim_id) on delete set null,
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table claim_payment(
    payment_id varchar(5), 
    claim_id varchar(5),
    recipient_name varchar(20) not null, 
    recipient_address varchar(20),
    payment_amount numeric(14,2),
    bank varchar(10),
    primary key (payment_id, claim_id),
    foreign key (claim_id) references claim on delete set null);

create table ach_transfer(
    payment_id varchar(5), 
    account_number varchar(12),
    routing_number varchar(9),
    claim_id varchar(5),
    recipient_name varchar(20), 
    recipient_address varchar(20),
    payment_amount numeric(14,2),
    bank varchar(10),
    primary key (payment_id, claim_id),
    foreign key (claim_id) references claim on delete set null);

create table checks(
    payment_id varchar(5), 
    check_number varchar(9),
    check_date varchar(10),
    claim_id varchar(5),
    recipient_name varchar(20), 
    recipient_address varchar(20),
    payment_amount numeric(14,2),
    bank varchar(10),
    primary key (payment_id, claim_id),
    foreign key (claim_id) references claim on delete set null);

create table policy_payment(
    payment_id varchar(5), 
    policy_id varchar(5),
    recipient_name varchar(20), 
    recipient_address varchar(20),
    payment_amount numeric(14,2),
    bank varchar(10),
    primary key (payment_id, policy_id),
    foreign key (policy_id) references policy on delete set null);

create table credit(
    payment_id varchar(5), 
    card_number varchar(16),
    expiary_date varchar(10),
    cvc_number  varchar(4),
    policy_id varchar(5),
    recipient_name varchar(20), 
    recipient_address varchar(20),
    payment_amount numeric(14,2),
    bank varchar(10),
    primary key (payment_id, policy_id),
    foreign key (policy_id) references policy on delete set null);

create table debit(
    payment_id varchar(20), 
    card_number varchar(16),
    expiary_date varchar(10),
    cvc_number  varchar(4),
    policy_id varchar(20),
    recipient_name varchar(20), 
    recipient_address varchar(20),
    payment_amount numeric(14,2),
    bank varchar(10),
    primary key (payment_id, policy_id),
    foreign key (policy_id) references policy on delete set null);

create table customer_agent(
    customer_id varchar(5),
    agent_id varchar(5),
    primary key (customer_id, agent_id),
    foreign key (customer_id) references customer(customer_id) on delete set null,
    foreign key (agent_id) references agent(agent_id) on delete set null);

create table communicates(
    agent_id varchar(5),
    adjuster_id varchar(5),
    primary key (agent_id, adjuster_id),
    foreign key (agent_id) references agent(agent_id) on delete set null,
    foreign key (adjuster_id) references adjuster(adjuster_id) on delete set null);

create table manages(
    employee_id varchar(5),
    claim_id varchar(5), 
    primary key (employee_id, claim_id),
    foreign key (employee_id) references employee on delete set null),
    foreign key (claim_id) references claim on delete set null);
 
create table customer_policy(
    customer_id varchar(5),
    policy_id varchar(5), 
    primary key (customer_id, policy_id),
    foreign key (customer_id) references customer(customer_id) on delete set null,
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table outsources(
    name varchar(20) not null,
    claim_id varchar(5), 
    primary key (name, claim_id),
    foreign key (name) references company(name) on delete set null,
    foreign key (claim_id) references claim(claim_id) on delete set null);

create table quotes(
    name varchar(20) not null,
    item_id varchar(5), 
    primary key (name, item_id),
    foreign key (name) references company(name) on delete set null,
    foreign key (item_id) references item(item_id) on delete set null);
