#!/bin/sh

export CFG=db/config.sql

export HOST=localhost
export PORT=5432

export PG_USER=postgres
export PG_PASSWORD=postgres

export DB_NAME=drgn
export DB_USER=drgn
export DB_PASSWORD=drgn

echo --						>  $CFG
echo \\set db_name $DB_NAME 			>> $CFG
echo \\set db_user_name $DB_USER 		>> $CFG
echo \\set db_user_password \'\\\'\'$DB_PASSWORD\'	>> $CFG

