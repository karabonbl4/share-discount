CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id 			BIGINT NOT NULL,
    role_id 			BIGINT NOT NULL,
    CONSTRAINT user_role_pk PRIMARY KEY (user_id, role_id),
    CONSTRAINT ur_user_fk FOREIGN KEY (user_id)
<<<<<<< HEAD
    	REFERENCES public.user (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT ur_role_fk FOREIGN KEY (role_id)
    	REFERENCES public.role (id) ON UPDATE CASCADE ON DELETE CASCADE
=======
    	REFERENCES public.user (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT ur_role_fk FOREIGN KEY (role_id)
    	REFERENCES public.role (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
>>>>>>> main
);