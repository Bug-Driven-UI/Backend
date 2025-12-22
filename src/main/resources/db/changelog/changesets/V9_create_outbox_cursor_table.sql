CREATE TABLE IF NOT EXISTS outbox_cursor
(
    id           INT primary key         not null,
    last_sent_at TIMESTAMP default now() not null
);

INSERT INTO outbox_cursor
SELECT 1, MIN(created_at)
FROM outbox_events
ON CONFLICT (id) DO NOTHING;