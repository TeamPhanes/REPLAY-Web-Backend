CREATE TYPE social_type_enum AS ENUM ('GOOGLE', 'KAKAO', 'NAVER');
CREATE TYPE level_enum AS ENUM ('EASY', 'HARD', 'NORMAL');
CREATE TYPE gathering_role_enum AS ENUM ('HOST', 'MEMBER');

CREATE TABLE "user"
(
    id              BIGSERIAL PRIMARY KEY,
    email           VARCHAR(255),
    gender          VARCHAR(255),
    nickname        VARCHAR(255) UNIQUE,
    profile_comment VARCHAR(255),
    profile_image   VARCHAR(255),
    social_id       VARCHAR(255),
    social_type     social_type_enum,
    email_mark      BOOLEAN,
    gender_mark     BOOLEAN,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE theme
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(20),
    image    VARCHAR(255),
    city     VARCHAR(5),
    state    VARCHAR(5),
    spot     VARCHAR(10),
    cafe     VARCHAR(15),
    address  VARCHAR(100),
    playtime INT,
    level    level_enum
);

CREATE TABLE theme_like
(
    id       BIGSERIAL PRIMARY KEY,
    theme_id BIGINT REFERENCES theme (id),
    user_id  BIGINT REFERENCES "user" (id)
);

CREATE TABLE theme_content
(
    id       BIGSERIAL PRIMARY KEY,
    theme_id BIGINT UNIQUE REFERENCES theme (id),
    image    VARCHAR(255),
    story    VARCHAR(255)
);

CREATE TABLE review
(
    id               BIGSERIAL PRIMARY KEY,
    content          VARCHAR(255),
    hint             INT,
    score            INT,
    success          BOOLEAN,
    theme_id         BIGINT REFERENCES theme (id),
    user_id          BIGINT REFERENCES "user" (id),
    number_of_player INT,
    level_review     VARCHAR(255),
    story_review     VARCHAR(255),
    theme_review     VARCHAR(255)
);

CREATE TABLE refresh_token
(
    id          BIGSERIAL PRIMARY KEY,
    expire_date TIMESTAMP,
    user_id     BIGINT REFERENCES "user" (id),
    token       VARCHAR(255)
);

CREATE TABLE participating_theme
(
    id       BIGSERIAL PRIMARY KEY,
    theme_id BIGINT REFERENCES theme (id),
    user_id  BIGINT REFERENCES "user" (id)
);

CREATE TABLE genre
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(8),
    theme_id BIGINT REFERENCES theme (id)
);

CREATE TABLE gathering
(
    id                 BIGSERIAL PRIMARY KEY,
    name               VARCHAR(20) NOT NULL,
    theme_id           BIGINT REFERENCES theme (id),
    capacity           INT         NOT NULL,
    date_time          TIMESTAMP   NOT NULL,
    registration_start TIMESTAMP   NOT NULL,
    registration_end   TIMESTAMP   NOT NULL,
    list_image         VARCHAR(255),
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP
);

CREATE TABLE gathering_member
(
    id           BIGSERIAL PRIMARY KEY,
    role         gathering_role_enum,
    gathering_id BIGINT REFERENCES gathering (id),
    user_id      BIGINT REFERENCES "user" (id)
);

CREATE TABLE gathering_content
(
    id           BIGSERIAL PRIMARY KEY,
    content      VARCHAR(255) DEFAULT '모임 소개글입니다',
    gathering_id BIGINT REFERENCES gathering (id),
    price        VARCHAR(8)
);

CREATE TABLE gathering_comment
(
    id           BIGSERIAL PRIMARY KEY,
    content      VARCHAR(255),
    gathering_id BIGINT REFERENCES gathering (id),
    parent_id    BIGINT,
    user_id      BIGINT REFERENCES "user" (id),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);
