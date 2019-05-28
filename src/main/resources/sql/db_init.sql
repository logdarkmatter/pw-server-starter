
DROP table if exists public.pw_user cascade;

CREATE TABLE public.pw_user (
	id BIGSERIAL NOT null,
	login varchar(50) NOT NULL,
	password_hash varchar(60) NOT NULL,
	first_name varchar(50) NULL,
	last_name varchar(50) NULL,
	email varchar(191) NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	CONSTRAINT pk_pw_user PRIMARY KEY (id),
	CONSTRAINT ux_pw_user_email UNIQUE (email),
	CONSTRAINT ux_pw_user_login UNIQUE (login)
);

DROP TABLE if exists public.authority cascade;
CREATE TABLE public.authority (
	name varchar(50) NOT NULL,
	CONSTRAINT pk_authority PRIMARY KEY (name)
);

DROP table if exists public.user_authority cascade;
CREATE TABLE public.user_authority (
	user_id BIGSERIAL NOT NULL,
	authority_name varchar(50) NOT NULL,
	CONSTRAINT user_authority_pkey PRIMARY KEY (user_id, authority_name),
	CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES public.authority(name),
	CONSTRAINT fk_pw_user_id FOREIGN KEY (user_id) REFERENCES public.pw_user(id)
);

DROP table if exists public.client cascade;
CREATE TABLE public.client (
	id BIGSERIAL NOT NULL,
	first_name varchar(50) NULL,
	last_name varchar(50) NULL,
	date_of_birth date NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	CONSTRAINT pk_client PRIMARY KEY (id)
);

DROP table if exists public.address cascade;
CREATE TABLE public.address (
	id BIGSERIAL NOT NULL,
	client_id int8 NOT NULL,
	address varchar(255) NULL,
	postal_code varchar(255) NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	CONSTRAINT pk_address PRIMARY KEY (id),
	CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES public.client(id)
);

DROP table if exists public.contact cascade;
CREATE TABLE public.contact (
	id BIGSERIAL NOT NULL,
	client_id int8 NOT NULL,
	contact_type varchar(255) NULL,
	contact varchar(255) NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	CONSTRAINT pk_contact PRIMARY KEY (id),
	CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES public.client(id),
	CONSTRAINT ux_contact_contact UNIQUE (contact)
);

DROP table if exists public.entity_audit_event cascade;
CREATE TABLE public.entity_audit_event (
	id bigserial NOT NULL,
	entity_id int8 NOT NULL,
	entity_type varchar(255) NOT NULL,
	"action" varchar(20) NOT NULL,
	entity_value text NULL,
	commit_version int4 NULL,
	last_modified_by varchar(100) NULL,
	last_modified_date timestamp NOT NULL,
	CONSTRAINT pk_entity_audit_event PRIMARY KEY (id)
);
CREATE INDEX idx_entity_audit_event_entity_id ON public.entity_audit_event USING btree (entity_id);
CREATE INDEX idx_entity_audit_event_entity_type ON public.entity_audit_event USING btree (entity_type);

DROP table if exists public.persistent_audit_event cascade;
CREATE TABLE public.persistent_audit_event (
	event_id int8 NOT NULL,
	principal varchar(50) NOT NULL,
	event_date timestamp NULL,
	event_type varchar(255) NULL,
	CONSTRAINT pk_persistent_audit_event PRIMARY KEY (event_id)
);
CREATE INDEX idx_persistent_audit_event ON public.persistent_audit_event USING btree (principal, event_date);

DROP table if exists public.persistent_audit_evt_data cascade;
CREATE TABLE public.persistent_audit_evt_data (
	event_id int8 NOT NULL,
	"name" varchar(150) NOT NULL,
	value varchar(255) NULL,
	CONSTRAINT persistent_audit_evt_data_pkey PRIMARY KEY (event_id, name),
	CONSTRAINT fk_evt_pers_audit_evt_data FOREIGN KEY (event_id) REFERENCES persistent_audit_event(event_id)
);
CREATE INDEX idx_persistent_audit_evt_data ON public.persistent_audit_evt_data USING btree (event_id);

DROP table if exists public.product cascade;
CREATE TABLE public.product (
	id BIGSERIAL NOT NULL,
	product_name varchar(255) NULL,
	product_code varchar(255) NULL,
	desription varchar(255) NULL,
	expire_date date NULL,
	brand varchar(255) null,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	CONSTRAINT pk_product PRIMARY KEY (id)
);

DROP table if exists public.	 cascade;
CREATE TABLE public.product_precautions (
	id BIGSERIAL NOT NULL,
	product_id int8 NOT NULL,
	description varchar(255) NULL,
	precaution_type varchar(255) NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	CONSTRAINT pk_product_precaution PRIMARY KEY (id),
	CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES public.product(id)
);


commit;

INSERT INTO public.client (first_name,last_name,date_of_birth,created_by,created_date,last_modified_by,last_modified_date) VALUES 
('Pedro','Perdigão','2019-04-09','perdigaop','2019-04-09 16:38:47.110',NULL,NULL);
INSERT INTO public.address (client_id,address,postal_code,created_by,created_date,last_modified_by,last_modified_date) VALUES 
(1,'Campo Grande 376','1749-024 Lisboa','perdigaop','2019-04-09 16:43:03.472',NULL,NULL)
,(1,'Av. José Malhoa 16','1070-159 Lisboa','perdigaop','2019-04-09 16:43:03.000',NULL,NULL);
INSERT INTO public.contact (client_id,contact_type,contact,created_by,created_date,last_modified_by,last_modified_date) VALUES 
(1,'EMAIL','teste@email.com','perdigaop','2019-04-09 16:39:48.437',NULL,NULL)
,(1,'PHONE','965452456','perdigaop','2019-04-09 16:39:48.000',NULL,NULL);
INSERT INTO public.pw_user (id,login,password_hash,first_name,last_name,email,created_by,created_date,last_modified_by,last_modified_date) VALUES 
(1,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','system','2019-04-09 17:42:59.918',NULL,NULL)
,(2,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','system','2019-04-09 17:43:46.676',NULL,NULL);
INSERT INTO public.authority (name) VALUES 
('ROLE_ADMIN')
,('ROLE_USER');
INSERT INTO public.user_authority (user_id,authority_name) VALUES 
(1,'ROLE_USER')
,(2,'ROLE_USER')
,(2,'ROLE_ADMIN');
commit;