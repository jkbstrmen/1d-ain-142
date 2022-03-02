# 1d-ain-142

Basic web application with simple REST API. 

## Task for lesson no. 4

- Implement data access layer (persistence/repository) for each library entity (Book, Customer, Borrowing).
- Each member of a team should implement one repository class (BookRepository, CustomerRepository, BorrowingRepository) and corresponding @Entity.
- Each service class should use repository class for CRUD operations over entity.

## Simple tutorial

- Install [Docker](https://docs.docker.com/get-docker/) and [DockerCompose](https://docs.docker.com/compose/install/)
- Run command in directory consisting docker-compose.yml to get postgres database up and running 


     docker-compose up -d 

- Add maven dependencies to pom.xml for postgres and jpa support
    
    
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

- configure SpringBoot connection to database in configuration file (should be in path: src/main/resources/application.properties)


    spring.datasource.url=jdbc:postgresql://localhost:5432/library
    spring.datasource.username=postgres
    spring.datasource.password=example
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL81Dialect
    
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true

- Use @Entity annotation for entities, and create interfaces extending JpaRepository for repositories as shown in code

## Something more

- Connect to your database using any DB IDE like [DBeaver](https://dbeaver.io/), [DataGrip](https://www.jetbrains.com/datagrip/) or even IntelliJ using View -> Tool Windows -> Database.
- View your data saved in SQL database tables