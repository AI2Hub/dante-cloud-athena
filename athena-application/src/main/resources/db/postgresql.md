CREATE USER athena WITH PASSWORD 'athena';
CREATE DATABASE dante_athena_v2 OWNER athena;
GRANT ALL PRIVILEGES ON DATABASE dante_athena_v2 TO athena;