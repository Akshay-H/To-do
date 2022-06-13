CREATE TABLE todo(
    id int primary key,
    name varchar(100) not null,
    status BOOL DEFAULT FALSE
);