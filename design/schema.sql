create table employee(
    employee_id numeric(6,0) not null,
    name varchar(30) not null,
    primary key (employee_id));

create table customer(
    customer_id numeric(6,0) not null,
    name varchar(30) not null,
    social_security numeric(9,0),
    age numeric(3,0),
    phone_number varchar(20),
    email varchar(40),
    zip_code numeric(5,0),
    city varchar(20),
    state varchar(20),
    address varchar(50),
    primary key (customer_id));
    
create table agent(
    agent_id numeric(6,0) not null,
    primary key (agent_id),
    foreign key (agent_id) references employee(employee_id) on delete set null);

create table adjuster(
    adjuster_id numeric(6,0) not null,
    primary key (adjuster_id),
    foreign key (adjuster_id) references employee(employee_id) on delete set null);

create table policy(
    customer_id numeric(6,0) not null,
    policy_id numeric(6,0) not null, 
    type varchar(9),
    cost numeric(6,2) check (cost > 0),
    coverage numeric(8,2),
    deductible numeric(6,2),
    copay numeric(6,2),
    coinsurance numeric(2,0),
    effective_date date,
    expire_date date,
    primary key (policy_id),
    foreign key (customer_id) references customer(customer_id) on delete cascade);

create table home_insurance(
    policy_id numeric(6,0) not null,
    city varchar(20),
    state varchar(20),
    zip_code numeric(5,0),
    address varchar(50),
    year_built numeric (4,0),
    condition varchar(10),
    square_foot numeric(6,2), 
    lot_size numeric(6,2),
    credit_score numeric(3,0),
    mortgage_payment numeric (6,2),
    market_value numeric (8,2),
    personal_property_replacement varchar(3),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete cascade);

create table auto_insurance(
    policy_id numeric(6,0) not null,
    year numeric(4,0),
    make varchar(10),
    model varchar(10),
    vin varchar(17),
    license_plate varchar(7),
    driver_license varchar(20),
    total_mileage numeric(6,0),
    annual_miles numeric(6,0),
    market_value numeric(8,2),
    age numeric(3,0),
    gender varchar(2),
    credit_score numeric(3,0),
    number_of_dependants numeric(1,0),
    traffic_violations varchar(3),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete cascade);

create table health_insurance(
    policy_id numeric(6,0) not null,
    plan_category varchar(3),
    out_of_pocket_maximum numeric(8,2),
    tobacco_use varchar(3),
    age varchar(2),
    pre_existing_conditions varchar(3),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete cascade);

create table life_insurance(
    policy_id numeric(6,0) not null,
    plan_category varchar(8),
    age varchar(2),
    gender varchar(1),
    tobacco_use varchar(3),
    occupation varchar(30),
    medical_status varchar(10),
    family_medical_history varchar(255),
    beneficiary_name varchar(30) not null,
    beneficiary_social_security numeric(9,0),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete cascade);

create table claim(
    claim_id numeric(6,0) not null, 
    customer_id numeric(6,0) not null,
    claim_type varchar(20),
    accident varchar(20),
    items_damaged varchar(3),
    description varchar(255),
    outcome varchar(10) default null,
    agent_notes varchar(255) default null,
    primary key (claim_id),
    foreign key (customer_id) references customer(customer_id) on delete set null);

create table company(
    name varchar(30) not null,
    type varchar(20), 
    phone_number varchar(20),
    primary key (name));
    
create table item(
    item_id numeric(6,0) not null, 
    claim_id numeric(6,0) not null,
    policy_id numeric(6,0) not null,
    item_type varchar(20),
    description varchar(255),
    primary key (item_id),
    foreign key (claim_id) references claim(claim_id) on delete set null,
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table claim_payment(
    payment_id numeric(6,0) not null, 
    claim_id numeric(6,0) not null,
    recipient_name varchar(30) not null, 
    recipient_address varchar(50),
    payment_amount numeric(8,2),
    bank varchar(20),
    primary key (payment_id, claim_id),
    foreign key (claim_id) references claim on delete set null);

create table ach_transfer(
    payment_id numeric(6,0) not null, 
    account_number numeric(12,0),
    routing_number numeric(9,0),
    claim_id numeric(6,0) not null,
    recipient_name varchar(30) not null, 
    recipient_address varchar(50),
    payment_amount numeric(8,2),
    bank varchar(20),
    primary key (payment_id, claim_id),
    foreign key (claim_id) references claim on delete set null);

create table checks(
    payment_id numeric(6,0) not null, 
    check_number numeric(9,0),
    check_date varchar(10),
    claim_id numeric(6,0) not null,
    recipient_name varchar(30) not null, 
    recipient_address varchar(50),
    payment_amount numeric(8,2),
    bank varchar(20),
    primary key (payment_id, claim_id),
    foreign key (claim_id) references claim on delete set null);

create table policy_payment(
    payment_id numeric(6,0) not null, 
    policy_id numeric(6,0) not null,
    recipient_name varchar(30), 
    recipient_address varchar(50),
    payment_amount numeric(8,2),
    bank varchar(20),
    primary key (payment_id, policy_id),
    foreign key (policy_id) references policy on delete set null);

create table credit(
    payment_id numeric(6,0) not null, 
    type varchar(10),
    card_number numeric(16,0),
    expiary_date numeric(10,0),
    cvv numeric(3,0),
    policy_id numeric(6,0) not null,
    recipient_name varchar(30) not null, 
    recipient_address varchar(50),
    payment_amount numeric(8,2),
    bank varchar(20),
    primary key (payment_id, policy_id),
    foreign key (policy_id) references policy on delete set null);

create table debit(
    payment_id numeric(20,0), 
    type varchar(10),
    card_number numeric(16,0),
    expiary_date numeric(10,0),
    cvv  numeric(4,0),
    policy_id numeric(20,0),
    recipient_name varchar(30) not null, 
    recipient_address varchar(50),
    payment_amount numeric(8,2),
    bank varchar(20),
    primary key (payment_id, policy_id),
    foreign key (policy_id) references policy on delete set null);

create table customer_agent(
    customer_id numeric(6,0) not null,
    agent_id numeric(6,0) not null,
    primary key (customer_id, agent_id),
    foreign key (customer_id) references customer(customer_id) on delete set null,
    foreign key (agent_id) references agent(agent_id) on delete set null);

create table communicates(
    agent_id numeric(6,0) not null,
    adjuster_id numeric(6,0) not null,
    primary key (agent_id, adjuster_id),
    foreign key (agent_id) references agent(agent_id) on delete set null,
    foreign key (adjuster_id) references adjuster(adjuster_id) on delete set null);
    
create table manages(
    employee_id numeric(6,0) not null,
    claim_id numeric(6,0) not null, 
    primary key (employee_id, claim_id),
    foreign key (employee_id) references employee on delete set null,
    foreign key (claim_id) references claim on delete set null);
 
create table customer_policy(
    customer_id numeric(6,0) not null,
    policy_id numeric(6,0) not null, 
    primary key (customer_id, policy_id),
    foreign key (customer_id) references customer(customer_id) on delete set null,
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table outsources(
    name varchar(20) not null,
    claim_id numeric(6,0) not null, 
    primary key (name, claim_id),
    foreign key (name) references company(name) on delete set null,
    foreign key (claim_id) references claim(claim_id) on delete set null);

create table quotes(
    name varchar(20) not null,
    item_id numeric(6,0) not null, 
    primary key (name, item_id),
    foreign key (name) references company(name) on delete set null,
    foreign key (item_id) references item(item_id) on delete set null);
