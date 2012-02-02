SET search_path = users, pg_catalog;

SELECT pg_catalog.setval('id_generator', 1000, true);


INSERT INTO status VALUES (0, 'inactive');
INSERT INTO status VALUES (1, 'active');
INSERT INTO status VALUES (2, 'blocked');


INSERT INTO roles VALUES ('user', 'user');
INSERT INTO roles VALUES ('admin', 'user');
INSERT INTO roles VALUES ('admin', 'admin');
INSERT INTO roles VALUES ('user', 'guest');



INSERT INTO users VALUES (1, 'SUPERUSER', 'superuser@supermail.su', '2011-11-04 00:00:00', '2011-12-22 05:17:49', 1, 'admin');
INSERT INTO simple_auth_data VALUES (1, 'admin', '241f193fff88625ccb286eac55f53a76fd1f6d9');
