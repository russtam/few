#!/bin/sh

. ./_conf.sh

export PGUSER=$DB_USER
export PGPASSWORD=$DB_PASSWORD


psql -h $HOST -p $PORT -U $DB_USER -d $DB_NAME -f $1

