Spring Boot Application - Full Stater
===================================
- - - - 

This application was generated using [Spring Initializr](https://start.spring.io/), you can find documentation and help at [https://docs.spring.io/spring-boot/docs/2.1.4.RELEASE/api/](https://docs.spring.io/spring-boot/docs/2.1.4.RELEASE/api/).

# Technologies #
- Spring Boot
- Spring Data
- Spring Security
- JWT
- Tomcat
- Hibernate
- Spring Websocket
- Lombok
- JPA
- PostgreSQL
- Fasterxml Jackson
- Zalando - Problem
- Commons Langs
- Orika BeanMapper

# Scope #
This application already has included a backend structure with JWT Login, User Roles and some repositories to start our project for a client-server application. With this code you can easally start an web application without many effort.

# Database #
I'm using a Postgres Database hosted on Heroku, you can must change the [application.yml](https://github.com/logdarkmatter/pw-server-starter/blob/master/src/main/resources/application.yml) file to setup the credentials to our own database.

In order to help you on setup our database there is an sql file [db_init.sql](https://github.com/logdarkmatter/pw-server-starter/blob/master/src/main/resources/sql/db_init.sql) that you can run, that craetes the tables and some default data.

## Tables ##
- pw_user 
- authority
- user_authority (ManyToMany with pw_user and authority)
- client
- address (OneToMany with client)
- contact (OneToMany with client)

# TUTORIAL VIDEOS #
- [PART 1](https://drive.google.com/file/d/1QLqjqgHUtxbIBZqvimWBe0MwZIcpnmhz/view?usp=sharing)
- [PART 2](https://drive.google.com/file/d/1OziO5keamiTCjGZl5405INFbFC5Qc5Mi/view?usp=sharing)
