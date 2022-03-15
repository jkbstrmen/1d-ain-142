# 1d-ain-142

Basic web application with simple REST API. 

## Task for lesson no. 5

- Finalize Repository layer - use @JoinColumns in Borrowing to reference Book and Customer.
- Use DTOs for communication between controllers and client.
- Organize your code according to best practices.

## Something more

### Difference between fetch type LAZY vs EAGER
https://www.baeldung.com/hibernate-lazy-eager-loading

### @Transactional
For the sake of simplicity - DB transaction does that all changes of database are commited to it at once at the end of such transaction.
So when we use @Transactional annotation on a method in Spring it means that all changes made on database (which happened inside this method) are commited to database if and only if whole this method finished and finished without throwing runtime exception.

For even more information see this post:
https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth
