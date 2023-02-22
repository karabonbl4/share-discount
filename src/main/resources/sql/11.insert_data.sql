INSERT INTO user (id, firstname, surname, phone_number, email, birthday) VALUES (1, 'Ivan', 'Ivanov', '+32311212', 'ivan@ivanov.by', '1999-10-10');
INSERT INTO trademark (id, title) VALUES (1, 'OZ');
INSERT INTO admin_trademark (user_id, trademark_id) VALUES (1, 1);
INSERT INTO coupon (name, start_date, end_date, discount, used, trademark_id) VALUES ('coupon1', '2011-11-11', '2011-11-12', 0.53, FALSE, 1);
INSERT INTO discount_policy (title, min_discount, max_discount, discount_step, trademark_id) VALUES ('OZpolicy', 0.05, 0.5, 0.05, 1);
INSERT INTO discount_card (name, number, discount, owner_user_id, discount_policy_id) VALUES ('gold_card', 123123, 0.3, 1, 1);
INSERT INTO purchase (name, transaction_dt, sum, user_id, coupon_id, card_id) VALUES ('purchase2', '2023-01-26', 10.7, 1, 1, 1);
INSERT INTO user_coupon (user_id, coupon_id) VALUES (1,1);

