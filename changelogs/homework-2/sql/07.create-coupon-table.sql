CREATE TABLE IF NOT EXISTS public.coupon
(
    id bigserial NOT NULL,
    name varchar,
    start_date date NOT NULL,
    end_date date NOT NULL,
    discount decimal(3, 2) default 0 check(discount >=0 and discount <= 1),
    used boolean default false,
    trademark_id bigint NOT NULL,
    PRIMARY KEY (id)
);