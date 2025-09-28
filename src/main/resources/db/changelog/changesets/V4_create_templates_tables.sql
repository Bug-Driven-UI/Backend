CREATE TABLE templates
(
    id                            UUID PRIMARY KEY,
    name                          VARCHAR(20)      NOT NULL UNIQUE,
    component_json                pg_catalog.jsonb NOT NULL,
    created_at_timestamp_ms       bigint           NOT NULL,
    last_modified_at_timestamp_ms bigint
);

CREATE INDEX idx_templates_names ON templates (name);

CREATE INDEX idx_templates_like_names ON templates (name text_pattern_ops)