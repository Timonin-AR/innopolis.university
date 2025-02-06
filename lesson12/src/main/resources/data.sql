create SCHEMA IF NOT EXISTS my;


CREATE TABLE IF NOT EXISTS my.note (
   noteId INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    topic VARCHAR NOT NULL,
    text VARCHAR NOT NULL,
    dateAndTime TIMESTAMP NOT NULL
)