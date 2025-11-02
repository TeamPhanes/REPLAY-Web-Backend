CREATE TABLE IF NOT EXISTS theme_metrics
(
    id          BIGINT   NOT NULL PRIMARY KEY,
    avg_rating  DECIMAL(3, 1),
    theme_ratio INTEGER,
    level_ratio INTEGER,
    story_ratio INTEGER,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_theme_ratio CHECK (theme_ratio BETWEEN 0 AND 100),
    CONSTRAINT chk_level_ratio CHECK (level_ratio BETWEEN 0 AND 100),
    CONSTRAINT chk_story_ratio CHECK (story_ratio BETWEEN 0 AND 100)
);