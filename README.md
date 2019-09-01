## Usage

`mvn tomcat7:run -Dspring.profiles.active={PROFILE_NAME}`

- 2 profiles: `dev`, `embedded`
- `dev` profile needs standalone MySql, configuration can be done in `dev.properties`
- `embedded` profile uses in memory H2  
 

## Tests

`mvn test`

## API

Api documentation is under `doc` folder

## Tech
- Java 8
- Spring MVC
- MySQL
