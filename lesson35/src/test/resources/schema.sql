CREATE SCHEMA IF NOT EXISTS tireService;

CREATE TABLE tireService.client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fio VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    date_of_birth TIMESTAMP,
    is_delete BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE tireService.car (
    id INT PRIMARY KEY AUTO_INCREMENT,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    number VARCHAR(255) NOT NULL
);

CREATE TABLE tireService."order" (
    id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT REFERENCES tireService.client(id),
    car_id INT REFERENCES tireService.car(id),
    price INT NOT NULL,
    details VARCHAR(255)
);