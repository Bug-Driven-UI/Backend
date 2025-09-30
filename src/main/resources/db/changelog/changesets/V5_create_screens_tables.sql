CREATE TABLE screens
(
    id                           UUID PRIMARY KEY,
    name                         VARCHAR(30)  NOT NULL UNIQUE,
    description                  VARCHAR(100) NOT NULL,
    current_published_version_id UUID,
    row_version                  BIGINT
);

CREATE TABLE screen_versions
(
    id                            UUID   NOT NULL,
    screen_id                     UUID   NOT NULL,
    version                       INT    NOT NULL CHECK (version > 0),
    screen_json                   JSONB  NOT NULL,
    created_at_timestamp_ms       BIGINT NOT NULL,
    last_modified_at_timestamp_ms BIGINT,
    row_version                   BIGINT,
    CONSTRAINT pk_screen_versions PRIMARY KEY (id),
    CONSTRAINT uq_screen_versions_version UNIQUE (screen_id, version)
);

ALTER TABLE screen_versions
    ADD CONSTRAINT fk_screen_versions_screen
        FOREIGN KEY (screen_id) REFERENCES screens (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE INDEX idx_screen_versions_screen_id ON screen_versions (screen_id);