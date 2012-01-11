call _conf.bat

set PGUSER=%DB_USER%
set PGPASSWORD=%DB_PASSWORD%

psql -h %HOST% -p %PORT% -U %DB_USER% -d %DB_NAME% -f %1

