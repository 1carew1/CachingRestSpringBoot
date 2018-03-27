# Database Setup

## Configuration

MySQL is the current DB being used. It is configured for Dev and Production in the `application-dev.properties` 
and the `application-prod.properties` respectively. There is also a DB configured for testing, this is a H2 DB.

Ensure when running the project you have the correct profile set so it uses the correct properties file.

The entry in the properties file for the DB setup is :

```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://db1:3306/cachetest?useSSL=false
spring.datasource.username=dbuser
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
```

The gradle build entry for the MySQL connector is :

```runtime 'mysql:mysql-connector-java'```

Note the URL for the datasource. db1 is the host that mysql is running on. This may need to be changed to an IP or an entry in the host file of your system can be added to point to the desired DB host.

The user for the db is `dbuser` and the password is `password`. The database name for the project is `cachetest`.

## Create Dev DB
Ensure Docker is installed.

A directory will need to be created where the DB data is stored locally.
```
cd .
mkdir mysql-data
```

When the directory is present the MySQL instance can be run via :

```docker run --name mysqldev-db -p 3306:3306 -v ~/mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=cachetest -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=password -d mysql:5.7.21```

Note the parameters :
- `-p` is exposing the port for MySQL
- `-v` sets the data directory
- `-e MYSQL_DATABASE` is the DB name, in this case is `cachetest` 
- `-e MYSQL_USER` is the DB user for the specified DB and `-e MYSQL_PASSWORD` is this user's password.

After the initial setup the Dev DB can be run via :

```docker start mysqldev-db```