# Weather Archive

App for storing geographical locations and their weather data downloaded from online services.

## Prerequisites

MySQL Server version 8.0.22 or newer.
JDK 15 or newer.

## MySQL database installation.

### Step 1

Navigate to the directory containing the file `weather_archive.sql` and connect to the MySQL server using the mysql client program, e.g.
```
mysqlsh -u root -p --sql
```

### Step 2

Create the database using the `source` command:
```
source weather_archive.sql
```

### Step 3

Create user and grant access to the created database:
```
CREATE USER 'weather_user' IDENTIFIED BY 'wpass';
GRANT ALL on weather_archive.* TO 'weather_user';
```

## Compile and run.
```
mvn javafx:run
```

## TODO

* Error handling.
* Location update.
* Weather data download from services.
