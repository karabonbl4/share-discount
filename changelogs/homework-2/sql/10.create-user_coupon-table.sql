create table if not exists public.user_coupon
(
	id bigserial not null,
	user_id bigint not null,
	coupon_id bigint not null,
	PRIMARY KEY (id)
);