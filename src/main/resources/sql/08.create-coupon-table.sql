CREATE TABLE IF NOT EXISTS public.coupon
(
    id 					BIGSERIAL,
    name 				VARCHAR,
    start_date 			DATE NOT NULL,
    end_date 			DATE NOT NULL,
    discount 			DECIMAL(3, 2) DEFAULT 0,
    used 				BOOLEAN DEFAULT FALSE,
    trademark_id 		BIGINT NOT NULL,
    CONSTRAINT coupon_id_pk PRIMARY KEY (id),
    CONSTRAINT coupon_trademark_fk FOREIGN KEY (trademark_id)
<<<<<<< HEAD
    	REFERENCES public.trademark (id) ON UPDATE CASCADE ON DELETE CASCADE,
=======
    	REFERENCES public.trademark (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
>>>>>>> main
    CONSTRAINT coupon_discount_check CHECK (discount >=0 and discount <= 1)
);