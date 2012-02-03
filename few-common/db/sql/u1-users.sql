--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: users; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA users;


SET search_path = users, pg_catalog;

--
-- Name: id_generator; Type: SEQUENCE; Schema: users; Owner: -
--

CREATE SEQUENCE id_generator
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: roles; Type: TABLE; Schema: users; Owner: -; Tablespace: 
--

CREATE TABLE roles (
    id character varying(20) NOT NULL,
    role character varying(20) NOT NULL
);


--
-- Name: secure_actions; Type: TABLE; Schema: users; Owner: -; Tablespace: 
--

CREATE TABLE secure_actions (
    key character varying(50) NOT NULL,
    parameters character varying(1024) NOT NULL,
    expire_time timestamp without time zone NOT NULL
);


--
-- Name: simple_auth_data; Type: TABLE; Schema: users; Owner: -; Tablespace: 
--

CREATE TABLE simple_auth_data (
    user_id integer NOT NULL,
    login character varying(50) NOT NULL,
    password character varying(50) NOT NULL
);


--
-- Name: TABLE simple_auth_data; Type: COMMENT; Schema: users; Owner: -
--

COMMENT ON TABLE simple_auth_data IS 'Внутренняя аутентификационная информация системы';


--
-- Name: status; Type: TABLE; Schema: users; Owner: -; Tablespace: 
--

CREATE TABLE status (
    id integer NOT NULL,
    name character varying(10)
);


--
-- Name: users; Type: TABLE; Schema: users; Owner: -; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    display_name character varying(50) NOT NULL,
    email character varying(128) NOT NULL,
    registration_time timestamp(0) without time zone NOT NULL,
    last_login_time timestamp(0) without time zone,
    status_id integer DEFAULT 0 NOT NULL,
    role_id character varying(20) DEFAULT "current_user"() NOT NULL
);


--
-- Name: TABLE users; Type: COMMENT; Schema: users; Owner: -
--

COMMENT ON TABLE users IS 'Абоненты';


--
-- Name: COLUMN users.id; Type: COMMENT; Schema: users; Owner: -
--

COMMENT ON COLUMN users.id IS 'Идентификатор записи, первичный ключ';


--
-- Name: pk_subscriber; Type: CONSTRAINT; Schema: users; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_subscriber PRIMARY KEY (id);


--
-- Name: secure_actions_pkey; Type: CONSTRAINT; Schema: users; Owner: -; Tablespace: 
--

ALTER TABLE ONLY secure_actions
    ADD CONSTRAINT secure_actions_pkey PRIMARY KEY (key);


--
-- Name: simple_auth_data_login_key; Type: CONSTRAINT; Schema: users; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_auth_data
    ADD CONSTRAINT simple_auth_data_login_key UNIQUE (login);


--
-- Name: simple_auth_data_pkey; Type: CONSTRAINT; Schema: users; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_auth_data
    ADD CONSTRAINT simple_auth_data_pkey PRIMARY KEY (user_id);


--
-- Name: status_pkey; Type: CONSTRAINT; Schema: users; Owner: -; Tablespace: 
--

ALTER TABLE ONLY status
    ADD CONSTRAINT status_pkey PRIMARY KEY (id);


--
-- Name: users_email_key; Type: CONSTRAINT; Schema: users; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: roles_idx; Type: INDEX; Schema: users; Owner: -; Tablespace: 
--

CREATE INDEX roles_idx ON roles USING btree (id);


--
-- Name: user_profile_idx; Type: INDEX; Schema: users; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX user_profile_idx ON user_profile USING btree (user_id, field_id);


--
-- Name: simple_auth_data_fk; Type: FK CONSTRAINT; Schema: users; Owner: -
--

ALTER TABLE ONLY simple_auth_data
    ADD CONSTRAINT simple_auth_data_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;


--
-- Name: user_profile_fk; Type: FK CONSTRAINT; Schema: users; Owner: -
--

ALTER TABLE ONLY user_profile
    ADD CONSTRAINT user_profile_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;


--
-- Name: users_fk1; Type: FK CONSTRAINT; Schema: users; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_fk1 FOREIGN KEY (status_id) REFERENCES status(id);


--
-- PostgreSQL database dump complete
--

