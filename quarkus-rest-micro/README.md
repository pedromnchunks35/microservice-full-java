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
- Setting up in kubernetes
```
https://raw.githubusercontent.com/keycloak/keycloak-quickstarts/latest/kubernetes/keycloak.yaml
```
- I went o that website 