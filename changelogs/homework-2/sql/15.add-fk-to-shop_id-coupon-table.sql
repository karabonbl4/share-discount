ALTER TABLE IF EXISTS public.coupon
    ADD CONSTRAINT "fk to shop_id" FOREIGN KEY (shop_id)
    REFERENCES public.shop (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;