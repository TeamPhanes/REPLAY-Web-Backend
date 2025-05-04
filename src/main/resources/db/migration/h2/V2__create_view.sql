CREATE VIEW theme_with_genres AS
SELECT *,
       (SELECT GROUP_CONCAT(g.name SEPARATOR ',')
        FROM genre g
        WHERE t.id = g.theme_id) AS genres
FROM theme t;

CREATE VIEW participate_theme_summary AS
SELECT p.user_id,
       t.id                                               AS theme_id,
       t.address,
       t.spot,
       t.cafe,
       t.name,
       t.image,
       t.level,
       t.playtime,
       t.genres,
       CAST((SELECT COALESCE(AVG(r2.score), 0)
             FROM review r2
             WHERE r2.theme_id = t.id) AS NUMERIC(10, 2)) AS total_rating,
       r.id                                               AS review_id,
       (SELECT COUNT(*)
        FROM review r3
        WHERE r3.theme_id = t.id)                         AS review_count,
       r.score,
       r.hint,
       r.number_of_player,
       r.theme_review,
       r.level_review,
       r.story_review,
       r.content,
       r.success
FROM participating_theme p
         JOIN theme_with_genres t ON p.theme_id = t.id
         LEFT JOIN review r ON t.id = r.theme_id;

CREATE OR REPLACE VIEW participate_gathering_summary AS
SELECT gm.user_id,
       g.id   AS gathering_id,
       g.name,
       t.address,
       t.spot,
       t.cafe,
       t.id   AS theme_id,
       t.name AS theme_name,
       t.image,
       t.genres,
       t.level,
       g.date_time,
       g.capacity
FROM gathering_member gm
         JOIN gathering g ON gm.gathering_id = g.id
         JOIN theme_with_genres t ON g.theme_id = t.id;

CREATE OR REPLACE VIEW like_gathering_summary AS
SELECT gl.user_id,
       gl.gathering_id,
       g.name,
       twg.address,
       twg.spot,
       twg.cafe,
       twg.id                         AS theme_id,
       twg.image,
       twg.name                       AS theme_name,
       twg.genres,
       twg.playtime,
       twg.level,
       g.capacity,
       (SELECT COUNT(*)
        FROM gathering_member gm
        WHERE gm.gathering_id = g.id) AS participant_count
FROM gathering_like gl
         JOIN gathering g ON gl.gathering_id = g.id
         JOIN theme_with_genres twg ON g.theme_id = twg.id;
