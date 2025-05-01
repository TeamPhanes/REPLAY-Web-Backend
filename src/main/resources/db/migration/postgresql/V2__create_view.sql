CREATE OR REPLACE VIEW participate_theme_summary AS
SELECT p.user_id,
       t.id                      AS themeId,
       t.address,
       t.spot,
       t.cafe,
       t.name,
       t.image,
       t.level,
       t.playtime,
       (SELECT STRING_AGG(g.name, ',')
        FROM genre g
        WHERE t.id = g.theme_id) AS genres,
       (SELECT COALESCE(AVG(r.score), 0)
        FROM review r
        WHERE r.theme_id = t.id) AS average_rating,
       r.id                      AS reviewId,
       (SELECT COUNT(*)
        FROM review r
        where r.theme_id = t.id) AS reviewCount,
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