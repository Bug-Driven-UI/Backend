CREATE TABLE color_styles
(
    id    UUID PRIMARY KEY,
    token VARCHAR(100) NOT NULL UNIQUE,
    color VARCHAR(10)  NOT NULL
);

CREATE INDEX idx_color_styles_token ON color_styles (token);

CREATE INDEX idx_color_styles_like_token ON color_styles (token text_pattern_ops)