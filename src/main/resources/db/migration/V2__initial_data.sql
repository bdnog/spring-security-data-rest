
-- password '123' -> {bcrypt}$2a$10$qOvNky8TXMD9hFgC2WKrS.hhfRU5r4mqDqjuOqgvdKJJFBHGIWZky
insert into user (name, email, password) values ('Admin', 'admin@test.com', '{bcrypt}$2a$10$qOvNky8TXMD9hFgC2WKrS.hhfRU5r4mqDqjuOqgvdKJJFBHGIWZky');
insert into user (name, email, password) values ('Agent', 'agent@test.com', '{bcrypt}$2a$10$qOvNky8TXMD9hFgC2WKrS.hhfRU5r4mqDqjuOqgvdKJJFBHGIWZky');
insert into user (name, email, password) values ('Customer', 'customer@test.com', '{bcrypt}$2a$10$qOvNky8TXMD9hFgC2WKrS.hhfRU5r4mqDqjuOqgvdKJJFBHGIWZky');

insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_AGENT');
insert into role (name) values ('ROLE_CUSTOMER');

insert into privilege (name) values ('CREATE_EXAMPLE');
insert into privilege (name) values ('READ_EXAMPLE');
insert into privilege (name) values ('UPDATE_EXAMPLE');
insert into privilege (name) values ('DELETE_EXAMPLE');

insert into user_roles (user_id, roles_id) values (1, 1);
insert into user_roles (user_id, roles_id) values (2, 2);
insert into user_roles (user_id, roles_id) values (3, 3);

insert into role_privileges (role_id, privileges_id) values (1, 1);
insert into role_privileges (role_id, privileges_id) values (1, 2);
insert into role_privileges (role_id, privileges_id) values (1, 3);
insert into role_privileges (role_id, privileges_id) values (1, 4);
insert into role_privileges (role_id, privileges_id) values (2, 2);
insert into role_privileges (role_id, privileges_id) values (2, 3);
insert into role_privileges (role_id, privileges_id) values (3, 2);

insert into example (name) values ('Example 1');
insert into example (name) values ('Example 2');
insert into example (name) values ('Example 3');
