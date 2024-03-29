CREATE TABLE IF NOT EXISTS public.discount_card
(
    id 					BIGSERIAL,
    name 				VARCHAR,
    number 				BIGINT NOT NULL,
    discount 			DECIMAL(3, 2) DEFAULT 0,
    is_confirm          BOOLEAN DEFAULT FALSE,
    is_rent             BOOLEAN DEFAULT FALSE,
    owner_user_id 		BIGINT NULL,
    discount_policy_id 	BIGINT NOT NULL,
    CONSTRAINT dc_id_pk PRIMARY KEY (id),
	CONSTRAINT dc_discount_check CHECK (discount >=0 AND discount <= 1),
	CONSTRAINT dc_discount_policy_fk FOREIGN KEY (discount_policy_id)
		REFERENCES public.discount_policy (id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT dc_user_fk FOREIGN KEY (owner_user_id)
	    REFERENCES public.user (id) ON UPDATE CASCADE ON DELETE CASCADE
);
