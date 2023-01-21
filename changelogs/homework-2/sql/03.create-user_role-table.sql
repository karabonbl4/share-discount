CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT user_role_pk PRIMARY KEY (user_id, role_id),
    CONSTRAINT ur_user_fk FOREIGN KEY (user_id) REFERENCES public.user(id),
    CONSTRAINT ur_role_fk FOREIGN KEY (role_id) REFERENCES public.role(id)
);