CREATE TABLE IF NOT EXISTS public.role
(
    id BIGSERIAL,
    name VARCHAR NOT NULL,
    CONSTRAINT role_id_pk PRIMARY KEY (id)
);