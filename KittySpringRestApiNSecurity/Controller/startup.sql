CREATE TABLE if not exists kitty
(
    uuid     uuid PRIMARY KEY,
    name     VARCHAR(255),
    birthday DATE,
    race     VARCHAR(255),
    color    VARCHAR(255),
    owner_id uuid
);
CREATE TABLE if not exists owner
(
    uuid     uuid PRIMARY KEY,
    name     VARCHAR(255),
    birthday DATE
);
CREATE TABLE if not exists kitty_kitty
(
    first_one  uuid,
    second_one uuid,
    PRIMARY KEY (first_one, second_one)
);