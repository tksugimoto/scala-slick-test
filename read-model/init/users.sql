CREATE TABLE users (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY
    , name VARCHAR(50) NOT NULL
    , age INT
    , address VARCHAR(50)
);


INSERT INTO users VALUES (1, 'John Smith', 35, 'New York');
INSERT INTO users VALUES (2, 'Hans Schmidt', null, null);
