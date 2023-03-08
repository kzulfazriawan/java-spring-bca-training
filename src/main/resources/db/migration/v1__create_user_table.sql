CREATE TABLE user (
    id bigint(50) NOT NULL PRIMARY KEY,
    name varchar(199) NOT NULL,
    email varchar(199) NOT NULL UNIQUE,
    password varchar(199) NOT NULL
)
