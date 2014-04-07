﻿-- Создание таблиц и связей между ними

CREATE TABLE BANKS(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255)
);

CREATE TABLE CLIENTS( 
    ID INT PRIMARY KEY AUTO_INCREMENT, 
    NAME VARCHAR(255),
    GENDER VARCHAR(6),
    EMAIL VARCHAR(100),
    PHONE VARCHAR(15),
    CITY VARCHAR(50),
    BANK_ID INT,
    FOREIGN KEY(BANK_ID) REFERENCES BANKS(ID)
); 

CREATE TABLE ACCOUNTS( 
    ID INT PRIMARY KEY AUTO_INCREMENT, 
    CLIENT_ID INT,
    ACCTYPE VARCHAR(1),
    BALANCE DECIMAL(10, 2),
    OVERDRAFT REAL,
    FOREIGN KEY(CLIENT_ID) 
	    REFERENCES CLIENTS(ID)
); 

-- Заполнение БД тестовыми данными
INSERT INTO BANKS (NAME) VALUES('My Bank');

INSERT INTO CLIENTS VALUES(1, 'Ivanov V.I.', 'MALE', 'ivanov_vi@mail.com', '+78121234567', 'Saint Petersburg', 1);
INSERT INTO CLIENTS VALUES(2, 'Grinev V.A.', 'MALE', 'grinev_va@mail.com', '+78121234567', 'Moscow', 1);
INSERT INTO CLIENTS VALUES(3, 'Petrova A.S.', 'FEMALE', 'petrova_as@mail.com', '+78121234567', 'Saint Petersburg', 1);

INSERT INTO ACCOUNTS VALUES(1, 1, 'S', 7000, 0);
INSERT INTO ACCOUNTS VALUES(2, 1, 'C', -100, 1000);
INSERT INTO ACCOUNTS VALUES(3, 2, 'S', 10000, 0);
INSERT INTO ACCOUNTS VALUES(4, 3, 'C', -1500, 5000);