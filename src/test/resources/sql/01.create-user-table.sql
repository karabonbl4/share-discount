CREATE TABLE IF NOT EXISTS public.user
(
    id 				    BIGSERIAL,
    firstname 			VARCHAR NOT NULL,
    surname 			VARCHAR NOT NULL,
    phone_number 		VARCHAR NOT NULL,
    email 				VARCHAR NOT NULL,
    birthday 			DATE,
    score 				DECIMAL(10, 2) DEFAULT 0,
    is_active 			BOOLEAN DEFAULT FALSE,
    CONSTRAINT user_id_pk PRIMARY KEY (id)
);