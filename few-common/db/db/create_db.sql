\encoding UTF8
\i db/config.sql

create role :db_user_name login unencrypted password :db_user_password nosuperuser inherit;
\echo creating database...
create database :db_name with owner = :db_user_name encoding = 'UTF-8';
