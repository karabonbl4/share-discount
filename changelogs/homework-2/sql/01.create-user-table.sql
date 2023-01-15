CREATE TABLE IF NOT EXISTS public.user
(
    id bigserial NOT NULL,
    firstname varchar NOT NULL,
    surname varchar NOT NULL,
    phone_number varchar NOT NULL,
    email varchar NOT NULL,
    birthday date,
    score decimal(10, 2) DEFAULT 0,
    is_active boolean default false,
    PRIMARY KEY (id)
);
