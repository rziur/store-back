# Wine store backend

The Wine store application is divided into two parts:
- Frontend or "frontal part", which is the public website accessed by users who want to consult and buy the offers.
- Backend or "administration part", which can only be accessed by site administrators and where they can create, consult or modify any information about offers, purchases, stores and users.

The site has the following pages:
- Public page where the best-selling offers are shown
- Registration page
- Login page
- Adjustment page to consult and modify the data of a user
- Password page
- Pages to create, consult, modify and delete business information
- Administration page (User management, Metrics, Health, Audits, Logs, Api)
- Dashboard page (to check the orders)

The database:
- wine_store_address: stores the basic location information of a store
- wine_store: stores the data of the establishments that publish the offers
- wine_stock: stores all the wine, offer, purchase, sale and inventory information
- wine_offer: stores the basic information of each offer
- wine_sale: stores the basic information of each sale produced
- wine_customer: stores the profile of registered clients of the site

If you want to sign in, you can try the default accounts:
- Administrator (login="admin" and password="admin")
- User (login="anne" and password="user").

***Wine store API***
 
   - [x] jhipster
   - [x] Monolithic application
   - [x] Separating the front-end and the API server
   - [x] Using Angular + TypeScript
   - [x] Bootstrap 4 
   - [x] Data binding, form validation
   - [x] Spring Boot (MAVEN, SPRING, SPRING MVC REST, SPRING DATA JPA)
   - [x] JSON Web Token (JWT) 
   - [x] MariaDB
   - [x] Liquibase
   - [x] i18n
   - [x] Automated tasks with maven
   - [x] CD and CI environments in Docker
   - [x] Test
   - [x] Deploy with Docker

### The folder structure 

src
└── Main
    └── java
        ├── com
        │   └── store
        │       ├── aop
        │       │   └── logging
        │       ├── config
        │       │   └── audit
        │       ├── domain
        │       ├── repository
        │       ├── security
        │       │   └── jwt
        │       ├── service
        │       │   └── dto
        │       │   └── impl
        │       │   └── mapper
        │       ├── web
        │       │   └── rest
        │       └── Resources
        │           ├── config
        │           │   └── liquibase
        │           ├── i18n
        │           │   
        │           └── templates

        ├── test
        │   ├── java
                  └── *

This application was generated using JHipster 6.9.1.

## Development

To start your application in the dev profile, run:

    ./mvnw

## Building for production

### Packaging as jar

To build the final jar and optimize the store application for production, run:

    ./mvnw -Pprod clean verify

To ensure everything worked, run:

    java -jar target/*.jar

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify

## Testing

To launch your application's tests, run:

    ./mvnw verify

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```


Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

```
./mvnw initialize sonar:sonar
```

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mariadb database in a docker container, run:

    docker-compose -f src/main/docker/mariadb.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mariadb.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw -Pprod verify jib:dockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems.