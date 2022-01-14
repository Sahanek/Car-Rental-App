CREATE TABLE cars (
 id bigint not null,
 color varchar(255),
 engine_capacity varchar(255),
 mileage varchar(255),
 model varchar(255),
 power varchar(255),
 production_date date,
 type varchar(255),
 version bigint not null,
 created_date timestamp not null,
 last_modified_date timestamp not null,
 primary key (id));

CREATE SEQUENCE car_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


CREATE TABLE clients (id bigint not null,
 birth_date date,
 credit_card_number varchar(255),
 first_name varchar(255),
 last_name varchar(255),
 phone varchar(255),
 version bigint not null,
 created_date timestamp not null,
 last_modified_date timestamp not null,
 primary key (id));


CREATE SEQUENCE client_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE employee_cars (
 employee_id bigint not null,
 car_id bigint not null);


CREATE TABLE employees (
 id bigint not null,
 first_name varchar(255),
 last_name varchar(255),
 phone varchar(255),
 position varchar(255),
 facility_id bigint,
 version bigint not null,
 created_date timestamp not null,
 last_modified_date timestamp not null,
 primary key (id));

CREATE SEQUENCE employee_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


CREATE TABLE facilities (
 id bigint not null,
 city varchar(255),
 house_num varchar(255),
 postcode varchar(255),
 street varchar(255),
 version bigint not null,
 created_date timestamp not null,
 last_modified_date timestamp not null,
 primary key (id));

CREATE SEQUENCE facility_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE rentals (
 id bigint not null,
 loan_amount decimal(19,2),
 rent_end_date date,
 rent_start_date date,
 client_id bigint,
 rent_car_id bigint,
 pickup_facility_id bigint,
 return_facility_id bigint,
 version bigint not null,
 created_date timestamp not null,
 last_modified_date timestamp not null,
 primary key (id));

CREATE SEQUENCE rental_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

ALTER TABLE employee_cars add constraint emp_cars_car_fk foreign key (car_id) references cars;
ALTER TABLE employee_cars add constraint emp_cars_emp_fk foreign key (employee_id) references employees;
ALTER TABLE employees add constraint emp_facility_fk foreign key (facility_id) references facilities;
ALTER TABLE rentals add constraint rentals_client_fk foreign key (client_id) references clients;
ALTER TABLE rentals add constraint rentals_pickup_fac_fk foreign key (pickup_facility_id) references facilities;
ALTER TABLE rentals add constraint rentals_car_fk foreign key (rent_car_id) references cars on delete cascade;
ALTER TABLE rentals add constraint rentals_return_fac_fk foreign key (return_facility_id) references facilities;