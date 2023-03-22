CREATE TABLE IF NOT EXISTS public.user_card
(
    id 					BIGSERIAL,
    user_id 			BIGINT NOT NULL,
    card_id 			BIGINT NOT NULL,
    rent_start 			TIMESTAMP NOT NULL,
    rent_end            TIMESTAMP NOT NULL,
    CONSTRAINT ucus_id_pk PRIMARY KEY (id),
	CONSTRAINT ucus_user_fk FOREIGN KEY (user_id)
		REFERENCES public.user (id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT ucus_card_fk FOREIGN KEY (card_id)
	    REFERENCES public.discount_card (id) ON UPDATE CASCADE ON DELETE CASCADE
);