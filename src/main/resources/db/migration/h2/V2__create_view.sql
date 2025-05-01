CREATE VIEW participate_theme_summary AS
SELECT p.user_id,
       t.id                       AS theme_id,
       t.address,
       t.spot,
       t.cafe,
       t.name,
       t.image,
       t.level,
       t.playtime,
       (SELECT GROUP_CONCAT(g.name SEPARATOR ',')
        FROM genre g
        WHERE t.id = g.theme_id)  AS genres,
       (SELECT COALESCE(AVG(r2.score), 0)
        FROM review r2
        WHERE r2.theme_id = t.id) AS total_rating,
       r.id                       AS review_id,
       (SELECT COUNT(*)
        FROM review r3
        WHERE r3.theme_id = t.id) AS review_count,
       r.score,
       r.hint,
       r.number_of_player,
       r.theme_review,
       r.level_review,
       r.story_review,
       r.content,
       r.success
FROM participating_theme p
         JOIN theme t ON p.theme_id = t.id
         LEFT JOIN review r ON t.id = r.theme_id;
