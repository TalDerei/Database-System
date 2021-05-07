create table employee(
    employee_id numeric(6,0),
    name varchar(30) not null,
    salary numeric(6,2),
    primary key (employee_id));

create table customer(
    customer_id numeric(6,0),
    name varchar(30) not null,
    social_security numeric(9,0),
    date_of_birth date,
    phone_number varchar(20),
    email varchar(40),
    zip_code numeric(5,0),
    city varchar(20),
    state varchar(20),
    address varchar(50),
    primary key (customer_id));
    
create table agent(
    agent_id numeric(6,0),
    primary key (agent_id),
    foreign key (agent_id) references employee(employee_id) on delete set null);

create table adjuster(
    adjuster_id numeric(6,0),
    primary key (adjuster_id),
    foreign key (adjuster_id) references employee(employee_id) on delete set null);

create table policy(
    customer_id numeric(6,0),
    policy_id numeric(6,0), 
    type varchar(9),
    plan varchar(6),
    cost numeric(6,2) check (cost > 0),
    coverage numeric(8,2),
    deductible numeric(6,2),
    coinsurance numeric(2,0),
    effective_date date,
    expire_date date,
    policy_status varchar(10), 
    primary key (policy_id),
    foreign key (customer_id) references customer(customer_id) on delete cascade);

create table home_insurance(
    policy_id numeric(6,0),
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
    plan_category varchar(20),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table auto_insurance(
    policy_id numeric(6,0),
    year numeric(4,0),
    make varchar(10),
    model varchar(10),
    vin varchar(17),
    license_plate varchar(7),
    driver_license varchar(20),
    total_mileage numeric(6,0),
    annual_miles numeric(6,0),
    market_value numeric(8,2),
    date_of_birth date,
    gender varchar(2),
    credit_score numeric(3,0),
    traffic_violations varchar(3),
    number_of_dependants numeric(1,0),
    state varchar(20),
    plan_category varchar(20),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table health_insurance(
    policy_id numeric(6,0),
    plan_category varchar(3),
    out_of_pocket_maximum numeric(8,2),
    estimated_copay numeric(6,2),
    tobacco_use varchar(3),
    date_of_birth date,
    pre_existing_conditions varchar(3),
    number_of_dependants numeric(1,0),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table life_insurance(
    policy_id numeric(6,0),
    plan_category varchar(15),
    date_of_birth date,
    gender varchar(1),
    tobacco_use varchar(3),
    occupation varchar(30),
    medical_status varchar(11),
    family_medical_history varchar(255),
    beneficiary_name varchar(30) not null,
    beneficiary_social_security numeric(9,0),
    primary key (policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table claim(
    claim_id numeric(6,0), 
    policy_id numeric(6,0),
    claim_type varchar(20),
    accident varchar(20),
    items_damaged varchar(3),
    description varchar(255),
    decision varchar(10) default null,
    adjuster_notes varchar(255) default null,
    amount numeric(8,2) default null,
    claim_status varchar(10), 
    primary key (claim_id),
    foreign key (policy_id) references policy(policy_id) on delete cascade);

create table company(
    name varchar(30) not null,
    type varchar(20), 
    phone_number varchar(20),
    primary key (name));
    
create table item(
    item_id numeric(6,0), 
    claim_id numeric(6,0),
    policy_id numeric(6,0),
    item_type varchar(20),
    item_value numeric(6,2),
    description varchar(255),
    primary key (item_id),
    foreign key (claim_id) references claim(claim_id) on delete set null,
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table claim_payment(
    payment_id numeric(6,0), 
    claim_id numeric(6,0),
    recipient_name varchar(30) not null, 
    recipient_address varchar(50),
    payment_amount numeric(8,2),
    bank varchar(20),
    payment_date date,
    status varchar(10) default 'pending';
    primary key (payment_id, claim_id),
    foreign key (claim_id) references claim(claim_id) on delete set null);

create table ach_transfer(
    payment_id numeric(6,0), 
    account_number numeric(12,0),
    routing_number numeric(9,0),
    claim_id numeric(6,0),
    primary key (payment_id, claim_id),
    foreign key (claim_id) references claim(claim_id) on delete set null);

create table checks(
    payment_id numeric(6,0), 
    check_number numeric(9,0),
    check_date varchar(10),
    claim_id numeric(6,0),
    account_number numeric(12,0),
    routing_number numeric(9,0),
    primary key (payment_id, claim_id),
    foreign key (claim_id) references claim(claim_id) on delete set null);

create table policy_payment(
    payment_id numeric(6,0), 
    policy_id numeric(6,0),
    recipient_name varchar(30), 
    recipient_address varchar(50),
    payment_amount numeric(6,2),
    bank varchar(20),
    payment_date date,
    status varchar(10) default 'pending';
    primary key (payment_id, policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table credit(
    payment_id numeric(6,0), 
    type varchar(10),
    card_number numeric(16,0),
    expiary_date numeric(4,0),
    cvv numeric(3,0),
    policy_id numeric(6,0),
    primary key (payment_id, policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table debit(
    payment_id numeric(6,0), 
    type varchar(10),
    card_number numeric(16,0),
    expiary_date numeric(4,0),
    cvv numeric(4,0),
    policy_id numeric(20,0),
    primary key (payment_id, policy_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table customer_agent(
    customer_id numeric(6,0),
    agent_id numeric(6,0),
    primary key (customer_id, agent_id),
    foreign key (customer_id) references customer(customer_id) on delete set null,
    foreign key (agent_id) references agent(agent_id) on delete set null);

create table communicates(
    agent_id numeric(6,0),
    adjuster_id numeric(6,0),
    primary key (agent_id, adjuster_id),
    foreign key (agent_id) references agent(agent_id) on delete set null,
    foreign key (adjuster_id) references adjuster(adjuster_id) on delete set null);

create table manages(
    employee_id numeric(6,0),
    claim_id numeric(6,0), 
    primary key (employee_id, claim_id),
    foreign key (employee_id) references employee on delete set null,
    foreign key (claim_id) references claim on delete set null);
 
create table customer_policy(
    customer_id numeric(6,0),
    policy_id numeric(6,0), 
    primary key (customer_id, policy_id),
    foreign key (customer_id) references customer(customer_id) on delete set null,
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table outsources(
    name varchar(20),
    claim_id numeric(6,0), 
    primary key (name, claim_id),
    foreign key (name) references company(name) on delete set null,
    foreign key (claim_id) references claim(claim_id) on delete set null);

create table quotes(
    name varchar(20) not null,
    item_id numeric(6,0), 
    primary key (name, item_id),
    foreign key (name) references company(name) on delete set null,
    foreign key (item_id) references item(item_id) on delete set null);

create table corperate(
    corperate_id numeric(6,0),
    primary key (corperate_id));

create table dependant(
    dependant_id numeric(6,0),
    policy_id numeric(6,0),
    name varchar(30) not null,
    social_security numeric(9,0),
    date_of_birth date,
    primary key (dependant_id), 
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table vehicle(
    policy_id numeric(6,0),
    vehicle_id numeric(6,0),
    extra_vehicle varchar(3),
    year numeric(4,0),
    make varchar(10),
    model varchar(10),
    vin varchar(17),
    license_plate varchar(7),
    total_mileage numeric(6,0),
    annual_miles numeric(6,0),
    market_value numeric(8,2),
    primary key (policy_id, vehicle_id),
    foreign key (policy_id) references policy(policy_id) on delete set null);

create table customer_claim(
    customer_id numeric(6,0),
    claim_id numeric(6,0), 
    primary key (customer_id, claim_id),
    foreign key (customer_id) references customer(customer_id) on delete set null,
    foreign key (claim_id) references claim(claim_id) on delete set null);
    
create table policy_includes(
    policy_id numeric(6,0),
    item_id numeric(6,0), 
    primary key (policy_id, item_id),
    foreign key (policy_id) references policy(policy_id) on delete set null,
    foreign key (item_id) references item(item_id) on delete set null);

create table claim_includes(
    claim_id numeric(6,0),
    item_id numeric(6,0), 
    primary key (claim_id, item_id),
    foreign key (claim_id) references claim(claim_id) on delete set null,
    foreign key (item_id) references item(item_id) on delete set null);