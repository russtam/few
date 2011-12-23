call _conf.bat

set PGUSER=%DB_USER%
set PGPASSWORD=%DB_PASSWORD%

mkdir schemas
pg_dump -h %HOST% -p %PORT% -U %DB_USER% -E UTF8 -O -s -n sessions -f schemas\sessions.sql %DB_NAME%
pg_dump -h %HOST% -p %PORT% -U %DB_USER% -E UTF8 -O -s -n users    -f schemas\users.sql    %DB_NAME%
