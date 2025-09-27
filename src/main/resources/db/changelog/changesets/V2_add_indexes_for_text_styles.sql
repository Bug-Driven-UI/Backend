CREATE INDEX idx_text_styles_token ON text_styles (token);

CREATE INDEX idx_text_styles_like_token ON text_styles (token text_pattern_ops)
