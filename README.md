\# Ticketing System



A REST API-based ticket management application built with Java and Spring Boot. The project demonstrates backend development concepts such as authentication, role-based access control, database persistence, and ticket lifecycle management.



\## Features



\- User authentication using JWT

\- Password hashing with BCrypt

\- Role-based access control

\- Ticket management REST APIs

\- Ticket comments

\- User management

\- Ticket categories, priorities, and statuses

\- Spring Data JPA for database operations

\- H2 database for development and testing



\## Tech Stack



\- \*\*Language:\*\* Java 17

\- \*\*Framework:\*\* Spring Boot

\- \*\*Build Tool:\*\* Maven

\- \*\*Database:\*\* H2

\- \*\*Persistence:\*\* Spring Data JPA / Hibernate

\- \*\*Security:\*\* Spring Security, JWT, BCrypt

\- \*\*Testing:\*\* JUnit / Spring Boot Test



\## Prerequisites



\- Java 17 or compatible JDK

\- No separate database installation is required for the default H2 configuration



\## Running the Project



1\. Clone or download this repository.

2\. Open a terminal in the project directory.

3\. Configure the `JWT\_SECRET` environment variable before starting the application.

4\. Run the tests:



&#x20;  ```powershell

&#x20;  .\\mvnw.cmd test

&#x20;  ```



5\. Start the application:



&#x20;  ```powershell

&#x20;  .\\mvnw.cmd spring-boot:run

&#x20;  ```



The application runs on `http://localhost:8080` by default.



\## Database



The development configuration uses an in-memory H2 database. Data is not intended to persist after the application shuts down.



\## API Documentation



API endpoints can be tested using Postman. Add a Postman collection or API documentation to this repository if you want others to reproduce your requests.



\## Security Note



Do not commit JWT secrets, passwords, tokens, or other credentials. Configure secrets through environment variables instead.



\## License



See the `LICENSE` file included in this repository.

