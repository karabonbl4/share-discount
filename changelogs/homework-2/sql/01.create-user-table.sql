CREATE TABLE IF NOT EXISTS public.user(
    id bigint NOT NULL,
    firstname text NOT NULL,
    surname text NOT NULL,
    phone_number text NOT NULL,
    email text NOT NULL,
    birthday timestamp with time zone NOT NULL,
    score bigint DEFAULT 0,
    PRIMARY KEY (id));
