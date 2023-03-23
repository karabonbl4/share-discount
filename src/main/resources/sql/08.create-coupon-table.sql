CREATE TABLE IF NOT EXISTS public.coupon
(
    id 					BIGSERIAL,
    name 				VARCHAR,
    start_date 			DATE NOT NULL,
    end_date 			DATE NOT NULL,
    discount 			DECIMAL(3, 2) DEFAULT 0,
    used 				BOOLEAN DEFAULT FALSE,
    trademark_id 		BIGINT NOT NULL,
    user_id             BIGINT NULL,
    purchase_id         BIGINT NULL,
    CONSTRAINT coupon_id_pk PRIMARY KEY (id),
    CONSTRAINT coupon_trademark_fk FOREIGN KEY (trademark_id)
    	REFERENCES public.trademark (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT coupon_user_fk FOREIGN KEY (user_id)
    	REFERENCES public.user (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT coupon_purchase_fk FOREIGN KEY (purchase_id)
    	REFERENCES public.purchase (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT coupon_discount_check CHECK (discount >=0 and discount <= 1)
);