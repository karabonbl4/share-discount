CREATE TABLE IF NOT EXISTS public.discount_policy
(
    id 					BIGSERIAL,
    title 				VARCHAR,
    min_discount 		DECIMAL(3, 2) DEFAULT 0,
    max_discount 		DECIMAL(3, 2) DEFAULT 0,
    discount_step 		DECIMAL(10, 2) DEFAULT 1000,
    trademark_id 		BIGINT NOT NULL,
    CONSTRAINT discount_policy_id_pk PRIMARY KEY (id),
    CONSTRAINT dp_trademark_fk FOREIGN KEY (trademark_id)
    	REFERENCES public.trademark (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT dp_min_discount_check CHECK (min_discount >=0 and min_discount <= 1),
    CONSTRAINT dp_man_discount_check CHECK (max_discount >=0 and max_discount <= 1)
);