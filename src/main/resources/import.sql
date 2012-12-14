-- You can use this file to load seed data into the database using SQL statements
--insert into Member (id, name, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212');
insert into Status (id, version, name) values (1,0, 'Open');
insert into Status (id, version, name) values (2,0, 'Closed');
--insert into Product (id, version, name, startDate) values (1,0, 'Demo product', '2012-12-01');
insert into Product (id, version, name, startDate) values (1,0, 'Demo product', null);
--insert into Item (id, version, name, product_id, status_id) values (-1, 0, 'Item1', 1, 1);
--insert into ItemEvent(id, version, item_id, date, status_id) values (-1, 0, -1, '2012-12-02', 1);
--insert into Item (id, version, name, product_id, status_id) values (-2, 0, 'Item2', 1, 2);
--insert into ItemEvent(id, version, item_id, date, status_id) values (-2, 0, -2, '2012-12-03', 1);
--insert into ItemEvent(id, version, item_id, date, status_id) values (-3, 0, -2, '2012-12-09', 2);