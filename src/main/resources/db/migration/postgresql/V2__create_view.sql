CREATE OR REPLACE VIEW theme_with_genres AS
SELECT *,
       (SELECT STRING_AGG(g.name, ',')
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

CREATE OR REPLACE VIEW participate_theme_summary AS
SELECT p.user_id,
       twg.id                       AS theme_id,
       twg.address,
       twg.spot,
       twg.cafe,
       twg.name,
       twg.image                    AS list_image,
       twg.level,
       twg.playtime,
       twg.genres,
       COALESCE(rar.rating, 0)      AS rating,
       r.id                         AS review_id,
       COALESCE(rc.review_count, 0) AS review_count,
       r.score,
       r.hint,
       r.number_of_player,
       r.theme_review,
       r.level_review,
       r.story_review,
       r.content,
       r.success
FROM participating_theme p
         JOIN theme_with_genres twg ON p.theme_id = twg.id
         LEFT JOIN review r ON twg.id = r.theme_id
         LEFT JOIN review_avg_rating rar ON twg.id = rar.theme_id
         LEFT JOIN review_count rc ON twg.id = rc.theme_id;

CREATE OR REPLACE VIEW participate_gathering_summary AS
SELECT gm.user_id,
       g.id      AS gathering_id,
       g.name,
       twg.address,
       twg.spot,
       twg.cafe,
       twg.id    AS theme_id,
       twg.name  AS theme_name,
       twg.image AS list_image,
       twg.genres,
       twg.level,
       twg.playtime,
       g.date_time,
       g.capacity,
       g.registration_end
FROM gathering_member gm
         JOIN gathering g ON gm.gathering_id = g.id
         JOIN theme_with_genres twg ON g.theme_id = twg.id;

CREATE OR REPLACE VIEW like_gathering_summary AS
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
       COALESCE(pmc.participant_count, 0) AS participant_count
FROM gathering_like gl
         JOIN gathering g ON gl.gathering_id = g.id
         JOIN theme_with_genres twg ON g.theme_id = twg.id
         LEFT JOIN gathering_member_count pmc
                   ON g.id = pmc.gathering_id;

CREATE OR REPLACE VIEW like_theme_summary AS
SELECT tl.user_id,
       twg.id                                                     AS theme_id,
       twg.address,
       twg.spot,
       twg.cafe,
       twg.name                                                   AS theme_name,
       twg.image                                                  AS list_image,
       twg.level,
       twg.genres,
       twg.playtime,
       COALESCE(rar.rating, 0)                                    AS rating,
       COALESCE(rc.review_count, 0)                               AS review_count,
       TRUE                                                       AS is_liked,
       CASE WHEN pt.theme_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_marked
FROM theme_like tl
         JOIN theme_with_genres twg ON tl.theme_id = twg.id
         LEFT JOIN participating_theme pt ON tl.user_id = pt.user_id AND tl.theme_id = pt.theme_id
         LEFT JOIN review_avg_rating rar ON twg.id = rar.theme_id
         LEFT JOIN review_count rc ON twg.id = rc.theme_id;

CREATE OR REPLACE VIEW participate_gathering_with_like AS
SELECT pgs.*,
       CASE
           WHEN gl.user_id IS NOT NULL THEN true
           ELSE false
           END                            AS is_liked,
       COALESCE(pmc.participant_count, 0) AS participant_count
FROM participate_gathering_summary pgs
         LEFT JOIN gathering_like gl
                   ON pgs.user_id = gl.user_id
                       AND pgs.gathering_id = gl.gathering_id
         LEFT JOIN gathering_member_count pmc
                   ON pgs.gathering_id = pmc.gathering_id;