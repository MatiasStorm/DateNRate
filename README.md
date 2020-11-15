# Date N' Rate
## Introduction
Second mandatory assignment on the 2. semester of the Computer Science education at KEA.

We had to create a dating website with the following requirements:
1. Users should be created with a user profile containing at least the following:
    * Name
    * Contact info
    * Picture
    * Description
    * Tags
2. Only the loggedin user should be able to edit and view its contact info.
3. Users should be able to chat with each other.
4. An admin should be able to remove users from the site.
5. Nice to have: Dynamic upload of profile pictures.

Our website comply with all the requirements and also implements a rating system.

The business idea was to create a regular dating site, but with the addition of ratings.
Each user should be able to rate another user on a fixed set of characteristics on a scale from 1-10.


## Structure
The `documents` folder contains all our design documents and diagram, and our assignment in the `Aflevering.DateNRate.pdf` file.

The program itself is located in the `DateNRate` directory.


## Run
1. Setup Database
Source the `database.sql` file in MySQL, to create the `dateNrate` database.
Can be done with this command `source database.sql` while loggedin to the MySQL server from the folder where `database.sql` is located.

2. Create application.properties
Navigate to `DateNRate/DateNRate/src/main/resources` and create a new file called `application.properties`.

Fill it with the following:
```
spring.datasource.url=jdbc:mysql://localhost:3306/dateNrate?serverTimezone=UTC
spring.datasource.username=[username]
spring.datasource.password=[password]
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
Replace `[username]` and `[password]` with the username and password of the MySQL user you used to create the database.

3. Run the application
Navigate to `DateNRate/DateNRate` and execute:
`./mvn spring-boot:run` - Linux
`./mvnw spring-boot:run` - Windows
 
Finally navigate to [localhost:8080](localhost:8080) to use the website.


## Contributors
[JoergenKoeldal](https://github.com/JoergenKoeldal)

[PVGlimoe](https://github.com/PVGlimoe)

[MatiasStorm](https://github.com/MatiasStorm)
