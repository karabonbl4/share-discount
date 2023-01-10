CREATE TABLE IF NOT EXISTS public.coupon(
    id bigint NOT NULL,
    title text,
    daterange daterange NOT NULL,
    category text NOT NULL,
    discount integer NOT NULL,
    shop_id bigint NOT NULL,
    PRIMARY KEY (id));