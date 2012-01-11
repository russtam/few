--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = users, pg_catalog;

--
-- Name: id_generator; Type: SEQUENCE SET; Schema: users; Owner: -
--

SELECT pg_catalog.setval('id_generator', 1000, true);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: users; Owner: -
--

INSERT INTO roles VALUES ('user', 'user');
INSERT INTO roles VALUES ('admin', 'user');
INSERT INTO roles VALUES ('admin', 'admin');
INSERT INTO roles VALUES ('user', 'guest');


--
-- Data for Name: status; Type: TABLE DATA; Schema: users; Owner: -
--

INSERT INTO status VALUES (0, 'inactive');
INSERT INTO status VALUES (1, 'active');
INSERT INTO status VALUES (2, 'blocked');


--
-- Data for Name: users; Type: TABLE DATA; Schema: users; Owner: -
--

INSERT INTO users VALUES (1, 'SUPERUSER', 'superuser@supermail.su', '2011-11-04 00:00:00', '2011-12-22 05:17:49', 1, 'admin');


--
-- Data for Name: simple_auth_data; Type: TABLE DATA; Schema: users; Owner: -
--

INSERT INTO simple_auth_data VALUES (1, 'admin', '241f193fff88625ccb286eac55f53a76fd1f6d9');


--
-- PostgreSQL database dump complete
--

