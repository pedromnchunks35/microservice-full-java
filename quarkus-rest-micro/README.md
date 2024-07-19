# Info
- Panache
  - Panache is JPA
  - We need to extend our entity to panacheentity or panacheentitybase
    - The base is for when we want to change the type of id
    - Also in the service when dealing with the repository we should use repositorypanachebase alongside with the correct id
  - active record pattern
    - Its a design pattern for ORM mockups
    - we will add quarkus-panache-mock for this
- Configs of db
```
quarkus.datasource.db-kind=postgresql 
quarkus.datasource.username=admin
quarkus.datasource.password=12341234

quarkus.datasource.jdbc.url=jdbc:postgresql://172.24.166.62:30001/micro

quarkus.hibernate-orm.database.generation=update
quarkus.hibernate-orm.log.sql=true
```
- We have the credentials and also the debug mode opened
# Keycloak setup
## 1
- I am creating the db with the kubernetes config of the db
- Then i will enter the db and create a db for keycloak called "keycloak"
- The configs for keycloak are also in the kubernetes config where i create a connection with postgresql
- Im initing a keyloak instance then
## 2
- Creating a realm called "micro-rest"
- Lets then create a client for quarkus
- Active the client authentication
- Active the Authorization
- Enable web origin to "/*"
- Chaging the resources configs to this new client for quarkus
## 3
- Create a role for user and another for admin
- I will create a admin just because yes
- Dont forget to set the password for it
- Assign it the role admin
## 4 
- Go to clients 
- Because you activated authorization
- Create the resources 
  - This the urls that roles will have access to
  - I will put here all the urls that are accepted to access, i will put "/*"
- Create a policie of whanever type you want
- I will create a role one adding admin and user
- Then add a permission, this permission uses policies and resources together to achieve the desirable behavior
  - We will add those that we created before