INSERT INTO USERS (username,password,enabled) values ('admin','admin',1);
INSERT INTO Authorities (username,authority) values ('admin','ROLE_ADMIN'),('admin','ROLE_USER');
INSERT INTO USERS (username,password,enabled) values ('user','user',1);
INSERT INTO Authorities (username,authority) values ('user','ROLE_USER');