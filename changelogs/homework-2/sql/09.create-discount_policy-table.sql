CREATE TABLE IF NOT EXISTS public.discount_policy
(
    id bigserial NOT NULL,
    title varchar,
    min_discount decimal(3, 2) default 0 check(min_discount >=0 and min_discount <= 1),
    max_discount decimal(3, 2) default 0 check(max_discount >=0 and max_discount <= 1),
    discount_step decimal(10, 2) DEFAULT 1000,
    trademark_id bigint not null,
    PRIMARY KEY (id)
);