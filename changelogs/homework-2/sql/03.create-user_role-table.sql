CREATE TABLE IF NOT EXISTS public.user_role
(
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (id)
);