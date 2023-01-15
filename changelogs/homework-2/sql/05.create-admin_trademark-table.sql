CREATE TABLE IF NOT EXISTS public.admin_trademark
(
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    trademark_id bigint NOT NULL,
    PRIMARY KEY (id)
);