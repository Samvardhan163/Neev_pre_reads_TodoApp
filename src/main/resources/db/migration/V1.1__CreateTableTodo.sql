CREATE TABLE todo(
    id bigint generated by default as identity,
    description varchar(254) not null,
    completed BOOLEAN NOT NULL,
    primary key(id)
);