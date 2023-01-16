create table if not exists public.user_coupon
(
	user_id bigint not null,
	coupon_id bigint not null,
	unique (user_id, coupon_id)
);