
DROP table if exists public.pw_user cascade;

CREATE TABLE public.pw_user (
	id int8 NOT NULL,
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
	user_id int8 NOT NULL,
	authority_name varchar(50) NOT NULL,
	CONSTRAINT user_authority_pkey PRIMARY KEY (user_id, authority_name),
	CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES public.authority(name),
	CONSTRAINT fk_pw_user_id FOREIGN KEY (user_id) REFERENCES public.pw_user(id)
);

DROP table if exists public.client cascade;
CREATE TABLE public.client (
	id int8 NOT NULL,
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
	id int8 NOT NULL,
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
	id int8 NOT NULL,
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

commit;

INSERT INTO public.client (id,first_name,last_name,date_of_birth,created_by,created_date,last_modified_by,last_modified_date) VALUES 
(1,'Pedro','Perdigão','2019-04-09','perdigaop','2019-04-09 16:38:47.110',NULL,NULL);
INSERT INTO public.address (id,client_id,address,postal_code,created_by,created_date,last_modified_by,last_modified_date) VALUES 
(1,1,'Campo Grande 376','1749-024 Lisboa','perdigaop','2019-04-09 16:43:03.472',NULL,NULL)
,(2,1,'Av. José Malhoa 16','1070-159 Lisboa','perdigaop','2019-04-09 16:43:03.000',NULL,NULL);
INSERT INTO public.contact (id,client_id,contact_type,contact,created_by,created_date,last_modified_by,last_modified_date) VALUES 
(1,1,'EMAIL','teste@email.com','perdigaop','2019-04-09 16:39:48.437',NULL,NULL)
,(2,1,'PHONE','965452456','perdigaop','2019-04-09 16:39:48.000',NULL,NULL);
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