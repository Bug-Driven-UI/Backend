CREATE TABLE text_styles
(
    id          UUID         NOT NULL,
    token       VARCHAR(100) NOT NULL,
    size        INTEGER      NOT NULL,
    weight      INTEGER,
    line_height INTEGER      NOT NULL,
    decoration  VARCHAR(20)  NOT NULL
)