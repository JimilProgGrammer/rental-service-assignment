DROP TABLE IF EXISTS car_rentals;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    useremail VARCHAR(255) NOT NULL,
    phone CHAR(15) NOT NULL,
    dob DATE NOT NULL
);

CREATE TABLE cars (
    car_id INT AUTO_INCREMENT PRIMARY KEY,
    crz VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE car_rentals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount FLOAT NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    location VARCHAR(255) NOT NULL,
    car_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY(car_id) REFERENCES cars(car_id),
    FOREIGN KEY(user_id) REFERENCES users(user_id)
);

INSERT INTO users (user_id, useremail, phone, dob) VALUES (1, 'johndoe@setu.com', '0123456789', '1999-10-16');
INSERT INTO users (user_id, useremail, phone, dob) VALUES (2, 'janedoe@setu.com', '0123456789', '1999-08-16');
INSERT INTO users (user_id, useremail, phone, dob) VALUES (3, 'barbaradoe@setu.com', '0123456789', '1999-09-16');

INSERT INTO cars (type, crz, user_id) VALUES ('SUV', 'BOM', 1);
INSERT INTO cars (type, crz, user_id) VALUES ('MVP', 'BLR', 1);
INSERT INTO cars (type, crz, user_id) VALUES ('SEDAN', 'HYD', 1);
INSERT INTO cars (type, crz, user_id) VALUES ('SUV', 'BLR', 2);
INSERT INTO cars (type, crz, user_id) VALUES ('SUV', 'BOM', 2);
INSERT INTO cars (type, crz, user_id) VALUES ('MVP', 'HYD', 3);
INSERT INTO cars (type, crz, user_id) VALUES ('MVP', 'BLR', 3);
INSERT INTO cars (type, crz, user_id) VALUES ('SUV', 'BOM', 3);
INSERT INTO cars (type, crz, user_id) VALUES ('SEDAN', 'BOM', 3);
INSERT INTO cars (type, crz, user_id) VALUES ('SEDAN', 'BLR', 3);
