-- You can use this file to load seed data into the database using SQL statements
insert into Status (id, name) values (1,'Open');
insert into Status (id, name) values (2,'Closed');

insert into Role (id, name) values (1, 'Product manager');
insert into Role (id, name) values (2, 'Contributor');
insert into Role (id, name) values (3, 'Readonly');

insert into UserDataName (id, name) values (1, 'First name');
insert into UserDataName (id, name) values (2, 'Last name');

insert into User (id, email) values (-1, 'demo1@example.com');
insert into User (id, email) values (-2, 'demo2@example.com');

insert into Product (id,  name, startDate) values (-1, 'Demo product 1', null);
insert into Product (id,  name, startDate) values (-2, 'Demo product 2', null);

insert into UserData (id,user_id, stringData, userDataName_id) values (-1,-1,'Demo', 1);
insert into UserData (id,user_id, stringData, userDataName_id) values (-2,-1,'User 1', 2);
insert into UserData (id,user_id, stringData, userDataName_id) values (-3,-2,'Demo', 1);
insert into UserData (id,user_id, stringData, userDataName_id) values (-4,-2,'User 2', 2);

insert into ProductUserRole (id, product_id, role_id, user_id) values (-1,-1,1,-1);
insert into ProductUserRole (id, product_id, role_id, user_id) values (-2,-2,2,-1);
insert into ProductUserRole (id, product_id, role_id, user_id) values (-3,-1,1,-2);
insert into ProductUserRole (id, product_id, role_id, user_id) values (-4,-2,2,-2);

--insert into Product (id, version, name, startDate) values (1,0, 'Demo product', '2012-12-01');
--insert into Item (id, version, name, product_id, status_id) values (-1, 0, 'Item1', 1, 1);
--insert into ItemEvent(id, version, item_id, date, status_id) values (-1, 0, -1, '2012-12-02', 1);
--insert into Item (id, version, name, product_id, status_id) values (-2, 0, 'Item2', 1, 2);
--insert into ItemEvent(id, version, item_id, date, status_id) values (-2, 0, -2, '2012-12-03', 1);
--insert into ItemEvent(id, version, item_id, date, status_id) values (-3, 0, -2, '2012-12-09', 2);