DROP TABLE IF EXISTS TMUser;
DROP TABLE IF EXISTS TMTask;

CREATE TABLE IF NOT EXISTS TMUser
(
    id TEXT NOT NULL PRIMARY KEY,
    nationalId VARCHAR ( 10 ) UNIQUE NOT NULL,
    name VARCHAR ( 50 ) NOT NULL,
    email VARCHAR ( 100 ) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS TMTask
(
    id TEXT NOT NULL PRIMARY KEY,
    title VARCHAR ( 100 ) NOT NULL,
    description VARCHAR ( 500 ) NOT NULL,
    userId VARCHAR ( 10 ) NOT NULL REFERENCES TMUser(nationalId)
);

/*
    postgres command line examples:
        psql -U postgres -d taskManagerdb
        SELECT * FROM INFORMATION_SCHEMA.tables where table_schema = 'public';
*/
