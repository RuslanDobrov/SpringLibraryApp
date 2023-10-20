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

# Spring Library Application
The system of library accounting of issued books. The system allows:
- add, edit and delete for people
- add, edit and delete for books
- add and delete books to people
- case insensitive search for title, author or people
- establishing the expiration date of a book
- pagination and sorting by title, author, year of published, quantity for books
- pagination and sorting by name or birthday for people
- input data validation for books and people
___
## How use
1. Create a database for the project:
   [schema.sql](https://github.com/RuslanDobrov/SpringLibraryApp/blob/master/src/main/java/ruslan/dobrov/sql/schema.sql)
2. Rename database connection settings:
   [hibernate.properties.origin](https://github.com/RuslanDobrov/SpringLibraryApp/blob/master/src/main/resources/hibernate.properties.origin) -> hibernate.properties
3. Enter your settings in hibernate.properties
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
![main_page](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/e2db7731-19cc-424a-a842-a9d5d1219c15)
### All books
![books_all](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/2fa6d99f-e2da-4cba-a7c3-436e43ec877d)
### Book create/edit
![books_new](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/97e1ab2c-c130-4bfb-92ac-8f741c5520ed)
![books_new_error](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/18eae82f-0889-4fbc-9313-aa7d634dd018)
### Book show
![books_show](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/9a22b2b3-f936-4e6c-8a95-cb3e1c393e97)
### All people
![people_all](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/46b08c38-4629-4d5d-a4aa-9a9535bce9af)
### Person create/edit
![people_new](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/4f892eef-b250-40b4-b69c-91edb189300b)
![people_new_error](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/82563b3e-b6d7-4113-92ea-026afce9995a)
### Person show
![people_show](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/cba63a4d-66b8-4535-8624-84eb4e7892cd)
### Search
![search_title](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/4063b329-4158-4d8c-a990-794a34762122)
![search_author](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/dcd1a764-e285-40d8-9490-e9d772b60092)
![search_person](https://github.com/RuslanDobrov/SpringLibraryApp/assets/17269289/26b49e8a-61ab-41b3-aa2d-c8b3cf39156b)

