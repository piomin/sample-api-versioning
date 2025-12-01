## Versioning REST API with Spring Boot and Swagger [![Twitter](https://img.shields.io/twitter/follow/piotr_minkowski.svg?style=social&logo=twitter&label=Follow%20Me)](https://twitter.com/piotr_minkowski)

[![CircleCI](https://circleci.com/gh/piomin/sample-api-versioning.svg?style=svg)](https://circleci.com/gh/piomin/sample-api-versioning)

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=piomin_sample-api-versioning)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=piomin_sample-api-versioning&metric=bugs)](https://sonarcloud.io/dashboard?id=piomin_sample-api-versioning)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=piomin_sample-api-versioning&metric=coverage)](https://sonarcloud.io/dashboard?id=piomin_sample-api-versioning)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=piomin_sample-api-versioning&metric=ncloc)](https://sonarcloud.io/dashboard?id=piomin_sample-api-versioning)

## Articles
1. Guide to API versiong with Spring Boot and a standard API offered by Spring MVC before official built-in support was available. Detailed description can be found here: [Versioning REST API with Spring Boot and Swagger](https://piotrminkowski.com/2018/02/19/versioning-rest-api-with-spring-boot-and-swagger/)
2. Guide to built-in API versioning feature available since Spring Boot 4. Detailed description can be found here: [Spring Boot Built-in API Versioning](https://piotrminkowski.com/2025/12/01/spring-boot-built-in-api-versioning/)


## API Versioning Implementation

This project demonstrates API versioning using Spring Boot 4's built-in support with the following features:

### Versioning Strategies

1. **URL Path Versioning** (Primary method used)
    - Example: `/persons/v1.0/1`
    - Configured in [application.yml](cci:7://file:///Users/pminkows/IdeaProjects/sample-api-versioning/src/main/resources/application.yml:0:0-0:0) with `path-segment: 1`

2. **Header-based Versioning** (Alternative method)
    - Header: `X-VERSION: v1.0`
    - Configured in [application.yml](cci:7://file:///Users/pminkows/IdeaProjects/sample-api-versioning/src/main/resources/application.yml:0:0-0:0)

### Versioned Models

1. **PersonOld (v1.0+)**
    - Fields: id, name, gender, birthDate (LocalDate)
    - Used by default for v1.0 and v1.1

2. **PersonCurrent (v1.2+)**
    - Fields: id, name, gender, age (int)
    - Introduced in v1.2

### Available Endpoints

#### Create Person
- **POST** `/persons/v1.0+` - Create person with birthDate (v1.0, v1.1)
- **POST** `/persons/v1.2` - Create person with age (v1.2)

#### Update Person
- **PUT** `/persons/v1.0` - Update person (deprecated in v1.0)
- **PUT** `/persons/v1.1/{id}` - Update person (v1.1)
- **PUT** `/persons/v1.2/{id}` - Update person (v1.2)

#### Get Person
- **GET** `/persons/v1.0/{id}` - Get person with birthDate (v1.0, v1.1)
- **GET** `/persons/v1.2/{id}` - Get person with age (v1.2)

#### Delete Person
- **DELETE** `/persons/v1.0/{id}` - Delete person (v1.0+)

### Configuration

The versioning is configured in [application.yml](cci:7://file:///Users/pminkows/IdeaProjects/sample-api-versioning/src/main/resources/application.yml:0:0-0:0):

```yaml
spring:
  mvc:
    apiversion:
      default: v1.0
      use:
        header: X-VERSION
        path-segment: 1
