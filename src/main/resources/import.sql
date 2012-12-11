-- You can use this file to load seed data into the database using SQL statements
insert into Member (id, name, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212');
insert into Status (id, version, name) values (1,0, 'Open');
insert into Status (id, version, name) values (2,0, 'Closed');
insert into Product (id, version, name) values (1,0, 'Demo product');