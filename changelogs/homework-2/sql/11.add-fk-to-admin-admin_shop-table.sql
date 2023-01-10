ALTER TABLE IF EXISTS public.admin_shop
    ADD CONSTRAINT "fk to admin" FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;