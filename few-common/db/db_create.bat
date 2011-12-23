call _conf.bat

set PGUSER=%PG_USER%
set PGPASSWORD=%PG_PASSWORD%

psql -h %HOST% -p %PORT% -U %PG_USER% -f database\create_db.sql

rem psql -U postgres -d %DB_NAME% -c "CREATE EXTENSION pgcrypto SCHEMA public;"
