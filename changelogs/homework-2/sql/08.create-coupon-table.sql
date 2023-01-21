CREATE TABLE IF NOT EXISTS public.coupon
(
    id BIGSERIAL,
    name VARCHAR,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    discount DECIMAL(3, 2) default 0 check(discount >=0 and discount <= 1),
    used BOOLEAN default false,
    trademark_id BIGINT NOT NULL,
    CONSTRAINT coupon_id_pk PRIMARY KEY (id),
    CONSTRAINT coupon_trademark_fk FOREIGN KEY (trademark_id) REFERENCES public.trademark (id) MATCH SIMPLE
);