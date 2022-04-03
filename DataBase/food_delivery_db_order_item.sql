create table order_item
(
    id       bigint auto_increment
        primary key,
    quantity int    not null,
    food_id  bigint not null,
    order_id bigint not null,
    constraint FK4fcv9bk14o2k04wghr09jmy3b
        foreign key (food_id) references food (id),
    constraint FK4x9b1ny7wu8uwe0w6vgdyp5ut
        foreign key (order_id) references food_order (id)
);

INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (1, 5, 6, 1);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (2, 2, 4, 1);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (3, 12, 5, 1);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (4, 1, 4, 2);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (5, 3, 1, 3);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (6, 5, 2, 4);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (7, 4, 1, 4);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (8, 1, 10, 5);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (9, 2, 11, 5);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (10, 6, 12, 5);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (11, 2, 12, 6);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (12, 2, 1, 7);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (13, 2, 12, 8);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (14, 1, 2, 9);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (15, 3, 1, 9);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (16, 1, 11, 10);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (17, 2, 10, 10);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (18, 1, 1, 11);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (19, 1, 20, 12);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (20, 1, 15, 12);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (21, 4, 2, 13);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (22, 1, 10, 14);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (23, 1, 17, 15);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (24, 2, 22, 15);
INSERT INTO food_delivery_db.order_item (id, quantity, food_id, order_id) VALUES (25, 4, 19, 15);