ALTER TABLE IF EXISTS public.user_role
    ADD CONSTRAINT "fk to user_id" FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.user_role
    ADD CONSTRAINT "fk to role_id" FOREIGN KEY (role_id)
    REFERENCES public.role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.admin_trademark
    ADD CONSTRAINT "fk to admin" FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.admin_trademark
    ADD CONSTRAINT "fk to trademark" FOREIGN KEY (trademark_id)
    REFERENCES public.trademark (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.discount_card
    ADD CONSTRAINT "fk user_id" FOREIGN KEY (owner_user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.discount_card
    ADD CONSTRAINT "fk discount_policy_id" FOREIGN KEY (discount_policy_id)
    REFERENCES public.discount_policy (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.coupon
    ADD CONSTRAINT "fk to trademark_id" FOREIGN KEY (trademark_id)
    REFERENCES public.trademark (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.user_coupon
    ADD CONSTRAINT "fk to coupon_id" FOREIGN KEY (coupon_id)
    REFERENCES public.coupon (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.user_coupon
    ADD CONSTRAINT "fk at user" FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.purchase
    ADD CONSTRAINT "fk to user" FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.purchase
    ADD CONSTRAINT "fk to coupon" FOREIGN KEY (coupon_id)
    REFERENCES public.coupon (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.purchase
    ADD CONSTRAINT "fk to discount_card_id" FOREIGN KEY (card_id)
    REFERENCES public.discount_card (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.discount_policy
    ADD CONSTRAINT "fk trademark" FOREIGN KEY (trademark_id)
    REFERENCES public.trademark (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;
