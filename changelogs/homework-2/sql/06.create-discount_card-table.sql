CREATE TABLE IF NOT EXISTS public.discount_card
(
    id bigserial NOT NULL,
    name varchar,
    "number" bigint NOT NULL,
    discount decimal(3, 2) default 0 check(discount >=0 and discount <= 1),
    owner_user_id bigint NOT NULL,
    discount_policy_id bigint not null,
    PRIMARY KEY (id)
);
