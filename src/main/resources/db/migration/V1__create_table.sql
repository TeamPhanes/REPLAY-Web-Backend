CREATE TABLE `user`
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    email           VARCHAR(255),
    gender          VARCHAR(255),
    nickname        VARCHAR(255) UNIQUE,
    profile_comment VARCHAR(255),
    profile_image   VARCHAR(255),
    social_id       VARCHAR(255),
    social_type     ENUM ('GOOGLE', 'KAKAO', 'NAVER'),
    email_mark      BOOLEAN,
    gender_mark     BOOLEAN,
    created_at      DATETIME,
    updated_at      DATETIME
);

CREATE TABLE theme
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(20),
    image    VARCHAR(255),
    city     VARCHAR(5),
    state    VARCHAR(5),
    spot     VARCHAR(10),
    cafe     VARCHAR(15),
    address  VARCHAR(100),
    playtime INT,
    level    ENUM ('EASY', 'HARD', 'NORMAL')
);

CREATE TABLE theme_like
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    theme_id BIGINT,
    user_id  BIGINT,
    CONSTRAINT FKhhvjlj02v9k3ccerce5uafjfg FOREIGN KEY (theme_id) REFERENCES theme (id),
    CONSTRAINT FKq4xku1e4ud3bdpr2g9jfn2xa1 FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE theme_content
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    theme_id BIGINT,
    image    VARCHAR(255),
    story    VARCHAR(255),
    CONSTRAINT UKcsyjx4mlcj6yaus8n7is1eqpi UNIQUE (theme_id),
    CONSTRAINT FK44vhbth7blonm1wu3umtm7650 FOREIGN KEY (theme_id) REFERENCES theme (id)
);

CREATE TABLE review
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
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
    CONSTRAINT FK93mqko4fwbvslo5p41hwpwgh9 FOREIGN KEY (theme_id) REFERENCES theme (id),
    CONSTRAINT FKiyf57dy48lyiftdrf7y87rnxi FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE refresh_token
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    expire_date DATETIME,
    user_id     BIGINT,
    token       VARCHAR(255),
    CONSTRAINT FKfgk1klcib7i15utalmcqo7krt FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE participating_theme
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    theme_id BIGINT,
    user_id  BIGINT,
    CONSTRAINT FKrohv97jwl3ovmbgooaymf9w3n FOREIGN KEY (user_id) REFERENCES `user` (id),
    CONSTRAINT FKrybfh3j7blcv6t2gebiekujid FOREIGN KEY (theme_id) REFERENCES theme (id)
);

CREATE TABLE genre
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(8),
    theme_id BIGINT,
    CONSTRAINT FKm0vfy7o8xhe0cdj84qvn0thxm FOREIGN KEY (theme_id) REFERENCES theme (id)
);

CREATE TABLE gathering
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(20) NOT NULL,
    theme_id           BIGINT,
    capacity           INT         NOT NULL,
    date_time          DATETIME    NOT NULL,
    registration_start DATETIME    NOT NULL,
    registration_end   DATETIME    NOT NULL,
    list_image         VARCHAR(255),
    created_at         DATETIME,
    updated_at         DATETIME,
    CONSTRAINT FKmkhfnqb1n7mcejnee05w7rq5j FOREIGN KEY (theme_id) REFERENCES theme (id)
);

CREATE TABLE gathering_member
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    role         ENUM ('HOST', 'MEMBER'),
    gathering_id BIGINT,
    user_id      BIGINT,
    CONSTRAINT FK6edvkrtl357dro8d0jxue9dn2 FOREIGN KEY (gathering_id) REFERENCES gathering (id),
    CONSTRAINT FK8deahy08876mkh2c0r6r8ody8 FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE gathering_content
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    content      VARCHAR(255) DEFAULT '모임 소개글입니다',
    gathering_id BIGINT,
    price        VARCHAR(8),
    CONSTRAINT FKc4c7sul15whvfxi8k007ghxap FOREIGN KEY (gathering_id) REFERENCES gathering (id)
);

CREATE TABLE gathering_comment
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    content      VARCHAR(255),
    gathering_id BIGINT,
    parent_id    BIGINT,
    user_id      BIGINT,
    created_at   DATETIME,
    updated_at   DATETIME,
    CONSTRAINT FKd7plj6wnu5fensg8c76b8cm34 FOREIGN KEY (gathering_id) REFERENCES gathering (id),
    CONSTRAINT FKhpvr0gjnc9l0ot7kc90n1wv1r FOREIGN KEY (user_id) REFERENCES `user` (id)
);
