Instructions

1. Before running the services, you have to create application.yml file, because it is set to git ignore
 to do that in the folder SwiftLogistics\backend\services\auth-service\auth-service\src\main\resources add a file called application.yml, here ypu must create resource folder first

and add this(you have to add your password and change username if needed),

spring:
  application:
    name: auth-service

  datasource:
    url: jdbc:postgresql://localhost:5432/swiftlogistics_authdb
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  secret: supersecretkeysupersecretkeysupersecretkey
  expiration: 3600000

server:
  port: 8081

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    fetch-registry: true
    register-with-eureka: true
  instance:
    prefer-ip-address: true

logging:
  level:
    org.springframework.security: DEBUG


I have added this application.yml only to auth service yet because other services are still in progress. Eureka server and Api gateway doesnot need this unless you want to change the port.

2. Now we have to setup database.

For auth service create a database named, swiftlogistics_authdb. Then create the users table(the DB script is given in DB script/auth_serivce.txt)
