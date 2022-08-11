# rental-service-assignment
Building a simple rental service with Spring Boot APIs

# Prerequisites
- Docker:         20.10.5, build 55c4c88
- docker-compose: 1.28.5, build c4eb3a1f

# Changing MySQL and Spring Boot ports:
- By default, the mysql container is exposed on your local machine via port `3306` and the Spring Boot container is exposed on port `8080`
- In case, these ports are occupied and you need to change the ports on your local machine, open the `docker-compose.yml` file at the repo root and change the following lines:<br/>
  - Line No.12: - `[YOUR NEW AVAILABLE PORT]:3306`
  - Line No.22: - `[YOUR NEW AVAILABLE PORT]:8080`

# Running the server with Docker
- Simply execute `docker-compose down && docker-compose up -d` at repository root
- Verify by executing `docker ps` - expected output will contain two images `setu_app_1` and `setu_mysqldb_1` in addition to any images that have been running locally on your system

# Initial data setup
- The Spring Boot server is loaded with an initialization `data.sql` script that creates the `rentals` database and the three required tables: `users`, `cars`, `car_rentals`
- Schema for the three tables are as mentioned below:<br/>
  `mysql> DESC users;`

  | Field     | Type         | Null | Key | Default | Extra          |
  |-----------|--------------|------|-----|---------|----------------|
  | user_id   | int          | NO   | PRI | NULL    | auto_increment |
  | useremail | varchar(255) | NO   |     | NULL    |                |
  | phone     | char(15)     | NO   |     | NULL    |                |
  | dob       | date         | NO   |     | NULL    |                |

  `mysql> DESC cars;`
  
  | Field   | Type         | Null | Key | Default | Extra          |
  |---------|--------------|------|-----|---------|----------------|
  | car_id  | int          | NO   | PRI | NULL    | auto_increment |
  | crz     | varchar(255) | NO   |     | NULL    |                |
  | type    | varchar(255) | NO   |     | NULL    |                |
  | user_id | int          | NO   | MUL | NULL    |                |

  `mysql> DESC car_rentals;`

  | Field     | Type         | Null | Key | Default | Extra          |
  |-----------|--------------|------|-----|---------|----------------|
  | id        | int          | NO   | PRI | NULL    | auto_increment |
  | amount    | float        | NO   |     | NULL    |                |
  | from_date | date         | NO   |     | NULL    |                |
  | to_date   | date         | NO   |     | NULL    |                |
  | location  | varchar(255) | NO   |     | NULL    |                |
  | car_id    | int          | NO   | MUL | NULL    |                |
  | user_id   | int          | NO   | MUL | NULL    |                |

- In addition, the `data.sql` file inserts some test data into the `users` and the `cars` table as below, so as to allow testing the functionality:<br/>
  `mysql> SELECT * FROM users;`
  
  | user_id | useremail           | phone      | dob        |
  |---------|---------------------|------------|------------|
  |       1 | johndoe@setu.com    | 0123456789 | 1999-10-16 |
  |       2 | janedoe@setu.com    | 0123456789 | 1999-08-16 |
  |       3 | barbaradoe@setu.com | 0123456789 | 1999-09-16 |

  `mysql> SELECT * FROM cars;`
  
  | car_id | crz | type  | user_id |
  |--------|-----|-------|---------|
  |      1 | BOM | SUV   |       1 |
  |      2 | BLR | MVP   |       1 |
  |      3 | HYD | SEDAN |       1 |
  |      4 | BLR | SUV   |       2 |
  |      5 | BOM | SUV   |       2 |
  |      6 | HYD | MVP   |       3 |
  |      7 | BLR | MVP   |       3 |
  |      8 | BOM | SUV   |       3 |
  |      9 | BOM | SEDAN |       3 |
  |     10 | BLR | SEDAN |       3 |

# Swagger UI
Once both the MySQL and Spring Boot containers are up and running, visit http://localhost:[YOUR NEW AVAILABLE PORT | 8080]/swagger-ui.html in your browser to access the Swagger UI and to try out the OpenAPI v3 Specification
