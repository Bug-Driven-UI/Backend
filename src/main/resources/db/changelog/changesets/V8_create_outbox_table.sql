CREATE TABLE outbox_events
(
    id         UUID PRIMARY KEY,
    event_type VARCHAR(100)            NOT NULL,
    payload    TEXT                    NOT NULL,
    created_at TIMESTAMP DEFAULT now() NOT NULL
) partition by RANGE (created_at)