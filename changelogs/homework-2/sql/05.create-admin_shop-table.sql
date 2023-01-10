CREATE TABLE IF NOT EXISTS public.admin_shop(
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    shop_id bigint NOT NULL,
    PRIMARY KEY (id));