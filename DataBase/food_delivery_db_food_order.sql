create table food_order
(
    id            bigint auto_increment
        primary key,
    customer_id   bigint       not null,
    restaurant_id bigint       not null,
    order_status  varchar(255) not null,
    constraint FK3mn6u8rd4f2ug91xmfwtkk51g
        foreign key (restaurant_id) references restaurant (id),
    constraint FKrqx1j12l31xmpih9gu5nbkcct
        foreign key (customer_id) references customer (id)
);

INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (1, 10, 7, 'X', '2022-04-01 12:26:20.636285');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (2, 17, 7, 'F', '2022-04-03 10:56:20.636285');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (3, 10, 6, 'P', '2022-04-02 12:26:20.636285');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (4, 16, 6, 'P', '2022-04-03 10:29:20.636285');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (5, 18, 15, 'X', '2022-04-03 11:10:20.636285');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (6, 40, 15, 'F', '2022-04-03 12:05:20.636285');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (7, 10, 6, 'P', '2022-04-03 10:26:20.636285');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (8, 10, 15, 'X', '2022-04-03 12:34:46.507469');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (9, 10, 6, 'P', '2022-04-03 16:38:30.794933');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (10, 25, 15, 'F', '2022-04-03 17:21:34.783697');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (11, 25, 6, 'X', '2022-04-03 17:53:09.929264');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (12, 16, 7, 'F', '2022-04-03 19:26:28.731613');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (13, 10, 6, 'P', '2022-04-03 20:04:28.749828');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (14, 16, 15, 'P', '2022-04-03 21:21:55.757342');
INSERT INTO food_delivery_db.food_order (id, customer_id, restaurant_id, order_status, date_time) VALUES (15, 16, 7, 'ID', '2022-04-03 21:23:38.782943');