CREATE TABLE external_apis (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    params TEXT NOT NULL,
    endpoints TEXT NOT NULL,
    schema TEXT,
    mapping_script TEXT,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL
);
