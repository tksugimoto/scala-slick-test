CREATE TABLE users (
    id BIGINT NOT NULL PRIMARY KEY
    , name VARCHAR(50) NOT NULL
    , age INT
);


INSERT INTO users VALUES (1, 'John Smith', 35);
INSERT INTO users VALUES (2, 'Hans Schmidt', null);
