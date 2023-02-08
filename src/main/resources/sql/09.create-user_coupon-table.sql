CREATE TABLE IF NOT EXISTS public.user_coupon
(
	user_id 			BIGINT NOT NULL,
	coupon_id 			BIGINT NOT NULL,
	CONSTRAINT user_coupon_pk PRIMARY key (user_id, coupon_id),
	CONSTRAINT uc_coupon_fk FOREIGN KEY (coupon_id)
		REFERENCES public.coupon (id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT uc_user_fk FOREIGN KEY (user_id)
		REFERENCES public.user (id) ON UPDATE CASCADE ON DELETE CASCADE
);
