#!/bin/sh

. ./_conf.sh

export PGUSER=$PG_USER
export PGPASSWORD=$PG_PASSWORD

psql -h $HOST -p $PORT -U $PG_USER -f db/drop_db.sql

