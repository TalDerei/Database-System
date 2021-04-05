create table employee(
    employee_id int,
    name varchar(20),
    primary key (employee_id));

create table customer(
    customer_id int,
    name varchar(20),
    address varchar(20),
    age int,
    primary key (customer_id));
    
create table agent(
    employee_id int,
    primary key (employee_id),
    foreign key (employee_id) references employee on delete set null);

create table adjuster(
    employee_id int,
    claim_id int,
    primary key (employee_id),
    foreign key (claim_id) references claim on delete set null);

create table policy(
    policy_id int, 
    customer_id int,
    policy_type varchar(20),
    policy_cost varchar(20) check (insurance_cost > 0)
    coverage varchar(20),
    primary key (policy_id),
    foreign key (customer_id) references customer on delete cascade);
    
create table claim(
    claim_id int, 
    customer_id int,
    claim_type varchar(20),
    description varchar(20),
    primary key (claim_id),
    foreign key (customer_id) references customer on delete cascade);

create table company(
    company_name varchar(20),
    claim_id int, 
    primary key (company_name, claim_id),
    foreign key (claim_id) references claim on delete set null);

create table item(
    item_id int, 
    claim_id int,
    item_type varchar(20),
    description varchar(20),
    primary key (item_id),
    foreign key (claim_id) references claim on delete set null);

create table payments(
    payment_id int, 
    payment_type varchar(20),
    amount numeric (8,2),
    primary key (payment_id));

create table claim_Payment(
    payment_id int, 
    claim_id int,
    primary key (payment_id),
    foreign key (claim_id) references claim on delete set null);
    
create table policy_Payment(
    payment_id int, 
    policy_id int,
    primary key (payment_id),
    foreign key (policy_id) references policy on delete set null);





























create table employee(
    employee_id int,
    name varchar(20),
    primary key (employee_id));

create table customer(
    customer_id int,
    name varchar(20),
    address varchar(20),
    age int,
    primary key (customer_id));
    
create table agent(
    employee_id int,
    primary key (employee_id),
    foreign key (employee_id) references employee on delete set null);

create table adjuster(
    employee_id int,
    primary key (employee_id),
    foreign key (claim_id) references claim on delete set null);

create table policy(
    policy_id int, 
    customer_id int,
    policy_type varchar(20),
    policy_cost varchar(20) check (insurance_cost > 0)
    coverage varchar(20),
    primary key (policy_id),
    foreign key (customer_id) references customer on delete cascade);
    
create table claim(
    claim_id int, 
    customer_id int,
    policy_id int, 
    claim_type varchar(20),
    claim_payment numeric (8,2),
    description varchar(20),
    primary key (claim_id),
    foreign key (customer_id) references customer on delete cascade),
    foreign key (policy_id) references policy on delete cascade);


create table customer_agent(
    customer_id int,
    employee_id int,
    primary key (customer_id, employee_id),
    foreign key (customer_id) references customer on delete set null),
    foreign key (employee_id) references employee on delete set null);

create table communicates(
    employee_id int,
    foreign key (employee_id) references employee on delete cascade),
    );

create table manages(
    employee_id int,
    claim_id int, 
    primary key (employee_id, claim_id),
    foreign key (employee_id) references employee on delete set null),
    foreign key (claim_id) references claim on delete set null);
 
create table customer_policy(
    customer_id int,
    policy_id int, 
    primary key (customer_id, emplloyee_id),
    foreign key (customer_id) references customer on delete set null),
    foreign key (policy_id) references policy on delete set null);