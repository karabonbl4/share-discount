CREATE TABLE IF NOT EXISTS public.trademark
(
    id BIGSERIAL,
    title VARCHAR NOT NULL,
    CONSTRAINT trademark_id_pk PRIMARY KEY (id)
);