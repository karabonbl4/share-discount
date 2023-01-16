CREATE TABLE IF NOT EXISTS public.admin_trademark
(
    user_id bigint NOT NULL,
    trademark_id bigint NOT NULL,
    unique (user_id, trademark_id)
);