![java version](https://img.shields.io/badge/Java-19-blue)
![java version](https://img.shields.io/badge/PostgreSQL-15-blue)
![java version](https://img.shields.io/badge/Spring-5-blue)
![java version](https://img.shields.io/badge/Bootstrap-5.3-blue)
![java version](https://img.shields.io/badge/Thymeleaf-grey)
![java version](https://img.shields.io/badge/HTML-grey)
![java version](https://img.shields.io/badge/CSS-grey)
![java version](https://img.shields.io/badge/JavaScript-grey)
![java version](https://img.shields.io/badge/Maven-grey)
![java version](https://img.shields.io/badge/Tomcat-9-blue)
![java version](https://img.shields.io/badge/GitHub-grey)
___

# SpringLibraryApp
The system of library accounting of issued books. The system allows:
- add new users
- edit users
- delete users
- add new books
- edit books
- delete books
- add books to users
- delete books from users
___
## How use
1. Create a database for the project

```postgresql
CREATE TABLE Person (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name varchar(100) NOT NULL UNIQUE,
    year_of_birth int NOT NULL
);
CREATE TABLE Book (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title varchar(100) NOT NULL,
    author varchar(100) NOT NULL,
    year int NOT NULL,
    person_id int REFERENCES Person(id) ON DELETE SET NULL
);
```

2. Rename database connection settings:
   database.properties.origin -> database.properties
3. Enter your settings in database.properties
```properties
# these settings are shown as an example
driver=org.postgresql.Driver
url=jdbc:postgresql://web.site:5432/spring_library_db
userDB=postgres
password=postgres
```

___
## Project screenshots
### About project
### Functionality
### Technologies
### All books
### Book edit
### Book new
### Book show
### All people
### Person edit
### Person new
### Person show