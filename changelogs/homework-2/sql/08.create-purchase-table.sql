CREATE TABLE IF NOT EXISTS public.purchase
(
    id bigserial NOT NULL,
    name varchar,
    transaction_dt timestamp NOT NULL,
    sum decimal(10, 2) NOT NULL,
    user_id bigint NOT NULL,
    coupon_id bigint,
    card_id bigint not null,
    PRIMARY KEY (id)
);