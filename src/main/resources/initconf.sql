DROP TABLE IF EXISTS fruit;
CREATE TABLE fruit
(
    id          SERIAL       NOT NULL UNIQUE,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id)
);

INSERT INTO fruit (id, name, description)
VALUES (100, 'Orange', 'Summer fruit');
INSERT INTO fruit (id, name, description)
VALUES (200, 'Strawberry', 'Winter fruit');