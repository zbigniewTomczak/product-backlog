-- You can use this file to load seed data into the database using SQL statements
insert into Status (id, name) values (1,'Open');
insert into Status (id, name) values (2,'Closed');

insert into Role (id, name) values (1, 'Product manager');
insert into Role (id, name) values (2, 'Contributor');
insert into Role (id, name) values (3, 'Readonly');

insert into UserDataName (id, name) values (1, 'First name');
insert into UserDataName (id, name) values (2, 'Last name');
