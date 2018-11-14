# JPA Mapping with H2DB using Hibernate

All possible **RDBMS** relationships have been explored in this project.
In order to prevent infinite recursion, **JsonBackReference** or **JsonIgnore**
have been applied in the models.

### Covered Relationships
- [x] One-to-One
- [x] One-to-Many
- [X] Many-to-One
- [x] Many-to-many

 ## Build
 ##### In Windows
 ``` ./gradle.bat clean build ``` if gradle is not installed on your machine
 
 ##### In Linux
 ``` ./gradlew clean build ``` if gradle is not installed on your machine
 
 ``` gradle clean build ``` if gradle is installed on your machine (for both Windows and Linux)
 
 ## Run
 ##### In Windows
 ``` ./gradle.bat clean bootRun ``` if gradle is not installed on your machine
 
 ##### In Linux
 ```./gradlew clean bootRun ``` if gradle is not installed on your machine
 
  ``` gradle clean bootRun ``` if gradle is installed on your machine (for both Windows and Linux)