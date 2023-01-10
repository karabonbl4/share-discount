CREATE TABLE IF NOT EXISTS public.user_role(
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (id));