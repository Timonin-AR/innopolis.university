create SCHEMA IF NOT EXISTS schemanotes;

create TABLE IF NOT EXISTS schemanotes.notes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(510),
    isActual BOOLEAN NOT NULL,
    isArchive BOOLEAN NOT NULL,
    createDate DATE NOT NULL
);