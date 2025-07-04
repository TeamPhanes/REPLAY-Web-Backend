CREATE TABLE IF NOT EXISTS users
(
    id              BIGSERIAL PRIMARY KEY,
    email           VARCHAR(50),
    gender          VARCHAR(2),
    nickname        VARCHAR(32) UNIQUE,
    profile_comment VARCHAR(255),
    profile_image   VARCHAR(200),
    social_id       VARCHAR(100),
    social_type     VARCHAR(10),
    email_mark      BOOLEAN,
    gender_mark     BOOLEAN,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE IF NOT EXISTS theme
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(20),
    image    VARCHAR(200),
    city  VARCHAR(10),
    state VARCHAR(10),
    spot  VARCHAR(20),
    cafe  VARCHAR(20),
    address  VARCHAR(100),
    playtime INT,
    level    VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS theme_like
(
    id       BIGSERIAL PRIMARY KEY,
    theme_id BIGINT REFERENCES theme (id),
    user_id  BIGINT REFERENCES users (id),
    CONSTRAINT uq_theme_like UNIQUE (theme_id, user_id)
);

CREATE TABLE IF NOT EXISTS theme_content
(
    id       BIGSERIAL PRIMARY KEY,
    theme_id BIGINT UNIQUE REFERENCES theme (id),
    image    VARCHAR(200),
    story    VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS review
(
    id               BIGSERIAL PRIMARY KEY,
    content          VARCHAR(255),
    image            VARCHAR(255),
    hint             INT,
    score            double precision,
    success          BOOLEAN,
    theme_id         BIGINT REFERENCES theme (id),
    user_id          BIGINT REFERENCES users (id),
    number_of_player INT,
    level_review     VARCHAR(6),
    story_review     VARCHAR(6),
    theme_review     VARCHAR(6),
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    CONSTRAINT uq_review UNIQUE (theme_id, user_id)
);

CREATE TABLE IF NOT EXISTS review_like
(
    id               BIGSERIAL PRIMARY KEY,
    review_id         BIGINT REFERENCES review (id),
    user_id          BIGINT REFERENCES users (id),
    CONSTRAINT uq_review_like UNIQUE (review_id, user_id)
);

CREATE TABLE IF NOT EXISTS refresh_token
(
    id          BIGSERIAL PRIMARY KEY,
    expire_date TIMESTAMP,
    user_id     BIGINT REFERENCES users (id),
    token       VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS theme_visit
(
    id       BIGSERIAL PRIMARY KEY,
    theme_id BIGINT REFERENCES theme (id),
    user_id  BIGINT REFERENCES users (id),
    CONSTRAINT uq_theme_visit UNIQUE (theme_id, user_id)
);

CREATE TABLE IF NOT EXISTS genre
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(8),
    theme_id BIGINT REFERENCES theme (id)
);

CREATE TABLE IF NOT EXISTS gathering
(
    id                 BIGSERIAL PRIMARY KEY,
    name               VARCHAR(20) NOT NULL,
    theme_id           BIGINT REFERENCES theme (id),
    capacity           INT         NOT NULL,
    date_time          TIMESTAMP   NOT NULL,
    registration_start TIMESTAMP   NOT NULL,
    registration_end   TIMESTAMP   NOT NULL,
    image              VARCHAR(200),
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gathering_member
(
    id           BIGSERIAL PRIMARY KEY,
    role         VARCHAR(10),
    gathering_id BIGINT REFERENCES gathering (id),
    user_id      BIGINT REFERENCES users (id),
    CONSTRAINT uq_gathering_member UNIQUE (gathering_id, user_id)
);

CREATE TABLE IF NOT EXISTS gathering_content
(
    id            BIGSERIAL PRIMARY KEY,
    content       VARCHAR(255) DEFAULT '모임 소개글입니다',
    gathering_id  BIGINT REFERENCES gathering (id),
    price         BIGINT,
    is_individual BOOLEAN,
    image         VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS gathering_comment
(
    id           BIGSERIAL PRIMARY KEY,
    content      VARCHAR(255),
    gathering_id BIGINT REFERENCES gathering (id),
    parent_id    BIGINT,
    user_id      BIGINT REFERENCES users (id),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gathering_like
(
    id           BIGSERIAL PRIMARY KEY,
    gathering_id BIGINT REFERENCES gathering (id),
    user_id      BIGINT REFERENCES users (id),
    CONSTRAINT uq_gathering_like UNIQUE (gathering_id, user_id)
);