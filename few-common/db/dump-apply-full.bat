call _conf.bat

set PGUSER=%DB_USER%
set PGPASSWORD=%DB_PASSWORD%

echo ===================  SCHEMAS  ===================
psql -h %HOST% -p %PORT% -U %DB_USER% -d %DB_NAME% -f schemas\users.sql
psql -h %HOST% -p %PORT% -U %DB_USER% -d %DB_NAME% -f schemas\sessions.sql

echo ===================  DATA     ===================
psql -h %HOST% -p %PORT% -U %DB_USER% -d %DB_NAME% -f data\users.sql
