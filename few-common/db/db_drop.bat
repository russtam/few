call _conf.bat

set PGUSER=%PG_USER%
set PGPASSWORD=%PG_PASSWORD%

psql -h %HOST% -p %PORT% -U %PG_USER% -f database\drop_db.sql

