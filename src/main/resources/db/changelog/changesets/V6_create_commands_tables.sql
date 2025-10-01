CREATE TABLE commands
(
    id                            UUID PRIMARY KEY,
    name                          VARCHAR(40)      NOT NULL UNIQUE,
    command_json                  pg_catalog.jsonb NOT NULL,
    created_at_timestamp_ms       bigint           NOT NULL,
    last_modified_at_timestamp_ms bigint
);

CREATE INDEX idx_commands_names ON commands (name);

CREATE INDEX idx_commands_like_names ON commands (name text_pattern_ops)