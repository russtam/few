set CFG=db\config.sql

set HOST=localhost
set PORT=5432

set PG_USER=postgres
set PG_PASSWORD=1Q2w3e4r

set DB_NAME=sample
set DB_USER=sample
set DB_PASSWORD=sample

echo --						>  %CFG%
echo \set db_name %DB_NAME% 			>> %CFG%
echo \set db_user_name %DB_USER% 		>> %CFG%
echo \set db_user_password '\''%DB_PASSWORD%'	>> %CFG%

