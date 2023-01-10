CREATE TABLE IF NOT EXISTS public.discount_card(
    id bigint NOT NULL,
    title text,
    "number" bigint NOT NULL,
    user_id bigint NOT NULL,
    shop_id bigint NOT NULL,
    PRIMARY KEY (id));
