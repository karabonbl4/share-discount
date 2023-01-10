ALTER TABLE IF EXISTS public.user_role
    ADD CONSTRAINT "fk to user_id" FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;
