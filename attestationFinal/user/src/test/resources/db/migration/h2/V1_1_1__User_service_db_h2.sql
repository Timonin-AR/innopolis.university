create SCHEMA IF NOT EXISTS smartnote;

CREATE TABLE IF NOT EXISTS smartnote.users (
    id SERIAL PRIMARY KEY,
    username varchar(50) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255),
    role varchar(50) NOT NULL,
    registered timestamp NOT NULL
);