--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: sessions; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA sessions;


SET search_path = sessions, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: tomcat_sessions; Type: TABLE; Schema: sessions; Owner: -; Tablespace: 
--

CREATE TABLE tomcat_sessions (
    session_id character varying(100) NOT NULL,
    valid_session character varying(1) NOT NULL,
    max_inactive integer NOT NULL,
    last_access bigint NOT NULL,
    app_name character varying(255),
    session_data bytea
);


--
-- Name: tomcat_sessions_pkey; Type: CONSTRAINT; Schema: sessions; Owner: -; Tablespace: 
--

ALTER TABLE ONLY tomcat_sessions
    ADD CONSTRAINT tomcat_sessions_pkey PRIMARY KEY (session_id);


--
-- PostgreSQL database dump complete
--

