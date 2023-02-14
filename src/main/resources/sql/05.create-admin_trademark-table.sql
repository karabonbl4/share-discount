CREATE TABLE IF NOT EXISTS public.admin_trademark
(
    user_id 			BIGINT NOT NULL,
    trademark_id 		BIGINT NOT NULL,
    CONSTRAINT at_user_trademark PRIMARY KEY (user_id, trademark_id),
    CONSTRAINT at_user_fk FOREIGN KEY (user_id)
<<<<<<< HEAD
    	REFERENCES public.user (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT at_trademark_fk FOREIGN KEY (trademark_id)
    	REFERENCES public.trademark (id) ON UPDATE CASCADE ON DELETE CASCADE
=======
    	REFERENCES public.user (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT at_trademark_fk FOREIGN KEY (trademark_id)
    	REFERENCES public.trademark (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
>>>>>>> main
);