CREATE TABLE users
(
    id              IDENTITY PRIMARY KEY,
    email           VARCHAR(255),
    gender          VARCHAR(255),
    nickname        VARCHAR(255) UNIQUE,
    profile_comment VARCHAR(255),
    profile_image   VARCHAR(255),
    social_id       VARCHAR(255),
    social_type     VARCHAR(20),
    email_mark      BOOLEAN,
    gender_mark     BOOLEAN,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE theme
(
    id       IDENTITY PRIMARY KEY,
    name     VARCHAR(20),
    image    VARCHAR(255),
    city     VARCHAR(5),
    state    VARCHAR(5),
    spot     VARCHAR(10),
    cafe     VARCHAR(15),
    address  VARCHAR(100),
    playtime INT,
    level    VARCHAR(20)
);

CREATE TABLE theme_like
(
    id       IDENTITY PRIMARY KEY,
    theme_id BIGINT,
    user_id  BIGINT,
    FOREIGN KEY (theme_id) REFERENCES theme (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE theme_content
(
    id       IDENTITY PRIMARY KEY,
    theme_id BIGINT UNIQUE,
    image    VARCHAR(255),
    story    VARCHAR(255),
    FOREIGN KEY (theme_id) REFERENCES theme (id)
);

CREATE TABLE review
(
    id               IDENTITY PRIMARY KEY,
    content          VARCHAR(255),
    hint             INT,
    score            INT,
    success          BOOLEAN,
    theme_id         BIGINT,
    user_id          BIGINT,
    number_of_player INT,
    level_review     VARCHAR(255),
    story_review     VARCHAR(255),
    theme_review     VARCHAR(255),
    FOREIGN KEY (theme_id) REFERENCES theme (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE refresh_token
(
    id          IDENTITY PRIMARY KEY,
    expire_date TIMESTAMP,
    user_id     BIGINT,
    token       VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE participating_theme
(
    id       IDENTITY PRIMARY KEY,
    theme_id BIGINT,
    user_id  BIGINT,
    FOREIGN KEY (theme_id) REFERENCES theme (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE genre
(
    id       IDENTITY PRIMARY KEY,
    name     VARCHAR(8),
    theme_id BIGINT,
    FOREIGN KEY (theme_id) REFERENCES theme (id)
);

CREATE TABLE gathering
(
    id                 IDENTITY PRIMARY KEY,
    name               VARCHAR(20) NOT NULL,
    theme_id           BIGINT,
    capacity           INT         NOT NULL,
    date_time          TIMESTAMP   NOT NULL,
    registration_start TIMESTAMP   NOT NULL,
    registration_end   TIMESTAMP   NOT NULL,
    list_image         VARCHAR(255),
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP,
    FOREIGN KEY (theme_id) REFERENCES theme (id)
);

CREATE TABLE gathering_member
(
    id           IDENTITY PRIMARY KEY,
    role         VARCHAR(10),
    gathering_id BIGINT,
    user_id      BIGINT,
    FOREIGN KEY (gathering_id) REFERENCES gathering (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE gathering_content
(
    id           IDENTITY PRIMARY KEY,
    content      VARCHAR(255) DEFAULT '모임 소개글입니다',
    gathering_id BIGINT,
    price        VARCHAR(8),
    FOREIGN KEY (gathering_id) REFERENCES gathering (id)
);

CREATE TABLE gathering_comment
(
    id           IDENTITY PRIMARY KEY,
    content      VARCHAR(255),
    gathering_id BIGINT,
    parent_id    BIGINT,
    user_id      BIGINT,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    FOREIGN KEY (gathering_id) REFERENCES gathering (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
