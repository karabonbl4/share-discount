CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    unique (user_id, role_id)
);