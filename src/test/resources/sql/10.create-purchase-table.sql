CREATE TABLE IF NOT EXISTS public.purchase
(
    id 					BIGSERIAL,
    name 				VARCHAR,
    transaction_dt 		TIMESTAMP NOT NULL,
    sum 				DECIMAL(10, 2) NOT NULL,
    user_id 			BIGINT NOT NULL,
    coupon_id 			BIGINT,
    card_id 			BIGINT NOT NULL,
    CONSTRAINT purchase_id_pk PRIMARY key (id),
    CONSTRAINT purchase_user_fk FOREIGN KEY (user_id)
    	REFERENCES public.user (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT purchase_coupon_fk FOREIGN KEY (coupon_id)
    	REFERENCES public.coupon (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT purchase_discount_card_fk FOREIGN KEY (card_id)
    	REFERENCES public.discount_card (id) ON UPDATE CASCADE ON DELETE CASCADE
);