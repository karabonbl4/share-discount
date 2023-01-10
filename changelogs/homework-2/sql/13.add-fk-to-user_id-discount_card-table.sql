ALTER TABLE IF EXISTS public.discount_card
    ADD CONSTRAINT "fk user_id" FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;