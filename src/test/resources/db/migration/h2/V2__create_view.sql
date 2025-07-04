CREATE OR REPLACE VIEW theme_with_genres AS
SELECT *,
       (SELECT GROUP_CONCAT(g.name SEPARATOR ',')
        FROM genre g
        WHERE t.id = g.theme_id) AS genres
FROM theme t;

CREATE OR REPLACE VIEW gathering_member_count AS
SELECT gathering_id,
       COUNT(*) AS participant_count
FROM gathering_member
GROUP BY gathering_id;

CREATE OR REPLACE VIEW review_avg_rating AS
SELECT theme_id,
       AVG(score) AS rating
FROM review
GROUP BY theme_id;

CREATE OR REPLACE VIEW review_count AS
SELECT theme_id,
       COUNT(*) AS review_count
FROM review
GROUP BY theme_id;

CREATE OR REPLACE VIEW review_like_count AS
SELECT review_id, COUNT(*) AS like_count
FROM review_like
GROUP BY review_id;

CREATE OR REPLACE VIEW theme_like_count AS
SELECT theme_id, COUNT(*) AS like_count
FROM theme_like
GROUP BY theme_id;

CREATE OR REPLACE VIEW theme_visit_with_review AS
SELECT tv.user_id,
       twg.id                                                  AS theme_id,
       twg.address,
       twg.spot,
       twg.cafe,
       twg.name,
       twg.image                                                 AS list_image,
       twg.level,
       twg.playtime,
       twg.genres,
       CAST(COALESCE(rar.rating, 0.0) AS DOUBLE)                 AS rating,
       r.id                                                      AS review_id,
       COALESCE(rc.review_count, 0)                              AS review_count,
       r.score,
       r.hint,
       r.number_of_player,
       r.theme_review,
       r.level_review,
       r.story_review,
       r.content,
       r.success,
       CASE WHEN tl.user_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_liked
FROM theme_visit tv
         JOIN theme_with_genres twg ON tv.theme_id = twg.id
         LEFT JOIN review r ON twg.id = r.theme_id
         LEFT JOIN review_avg_rating rar ON twg.id = rar.theme_id
         LEFT JOIN review_count rc ON twg.id = rc.theme_id
         LEFT JOIN theme_like tl ON tv.user_id = tl.user_id AND twg.id = tl.theme_id;

CREATE OR REPLACE VIEW gathering_participant AS
SELECT gm.user_id,
       gm.role,
       g.id                                                           AS gathering_id,
       g.name,
       twg.address,
       twg.spot,
       twg.cafe,
       twg.id                                                         AS theme_id,
       twg.name                                                       AS theme_name,
       twg.image                                                      AS list_image,
       twg.genres,
       twg.level,
       twg.playtime,
       g.date_time,
       g.capacity,
       g.registration_end,
       COALESCE(gmc.participant_count, 0) AS participant_count
FROM gathering_member gm
         JOIN gathering g ON gm.gathering_id = g.id
         JOIN theme_with_genres twg ON g.theme_id = twg.id
         LEFT JOIN gathering_member_count gmc on gm.gathering_id = gmc.gathering_id;

CREATE OR REPLACE VIEW gathering_like_with_participant AS
SELECT gl.user_id,
       gl.gathering_id,
       g.name,
       twg.address,
       twg.spot,
       twg.cafe,
       twg.id                             AS theme_id,
       twg.image                          AS list_image,
       twg.name                           AS theme_name,
       twg.genres,
       twg.playtime,
       twg.level,
       g.date_time,
       g.capacity,
       g.registration_end,
       IFNULL(pmc.participant_count, 0) AS participant_count
FROM gathering_like gl
         JOIN gathering g ON gl.gathering_id = g.id
         JOIN theme_with_genres twg ON g.theme_id = twg.id
         LEFT JOIN gathering_member_count pmc ON g.id = pmc.gathering_id;

CREATE OR REPLACE VIEW theme_like_with_review_summary AS
SELECT tl.user_id,
       twg.id                                               AS theme_id,
       twg.address,
       twg.spot,
       twg.cafe,
       twg.name                                             AS theme_name,
       twg.image                                            AS list_image,
       twg.level,
       twg.genres,
       twg.playtime,
       CAST(COALESCE(rar.rating, 0.0) AS DOUBLE)                  AS rating,
       COALESCE(rc.review_count, 0)                               AS review_count,
       CASE WHEN tv.theme_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_marked
FROM theme_like tl
         JOIN theme_with_genres twg ON tl.theme_id = twg.id
         LEFT JOIN theme_visit tv ON tl.user_id = tv.user_id AND tl.theme_id = tv.theme_id
         LEFT JOIN review_avg_rating rar ON twg.id = rar.theme_id
         LEFT JOIN review_count rc ON twg.id = rc.theme_id;

CREATE OR REPLACE VIEW gathering_schedule AS
SELECT gp.*
FROM gathering_participant gp;

CREATE OR REPLACE VIEW theme_list_with_review_and_liked_and_marked AS
SELECT twg.id                                                    AS theme_id,
       twg.name                                                  AS theme_name,
       twg.address,
       twg.spot,
       twg.cafe,
       twg.level,
       twg.playtime,
       twg.image                                                 AS list_image,
       twg.genres,
       COALESCE(rar.rating, 0)                                   AS rating,
       COALESCE(rc.review_count, 0)                              AS review_count,
       COALESCE(tlc.like_count, 0)                               AS like_count,
       tl.user_id,
       CASE WHEN tl.user_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_liked,
       CASE WHEN tv.user_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_marked
FROM theme_with_genres twg
         LEFT JOIN review_avg_rating rar ON twg.id = rar.theme_id
         LEFT JOIN review_count rc ON twg.id = rc.theme_id
         LEFT JOIN theme_like tl ON tl.theme_id = twg.id
         LEFT JOIN theme_visit tv ON tv.theme_id = twg.id AND tv.user_id = tl.user_id
         LEFT JOIN theme_like_count tlc ON tlc.theme_id = twg.id;

CREATE OR REPLACE VIEW gathering_list_with_theme_and_participant_and_liked AS
SELECT g.id AS gathering_id,
       g.name,
       twg.image AS list_image,
       twg.genres,
       twg.playtime,
       twg.address,
       twg.cafe,
       twg.spot,
       twg.level,
       g.date_time,
       g.registration_end,
       g.capacity,
       gmc.participant_count,
       CASE WHEN gl.user_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_liked
FROM gathering g
         LEFT JOIN theme_with_genres twg ON g.theme_id = twg.id
         LEFT JOIN gathering_like gl ON g.id = gl.gathering_id
         LEFT JOIN gathering_member_count gmc on g.id = gmc.gathering_id