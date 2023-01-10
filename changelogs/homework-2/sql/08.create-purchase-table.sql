CREATE TABLE IF NOT EXISTS public.purchase(
    id bigint NOT NULL,
    title text,
    datetime_purchase timestamp with time zone NOT NULL,
    sum numeric NOT NULL,
    user_id bigint NOT NULL,
    PRIMARY KEY (id));