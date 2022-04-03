create table cart_item
(
    id          bigint auto_increment
        primary key,
    quantity    int    not null,
    customer_id bigint not null,
    food_id     bigint not null,
    constraint FKcro8349ry4i72h81en8iw202g
        foreign key (food_id) references food (id),
    constraint FKfy7fubprxqguyp4km04eogy66
        foreign key (customer_id) references customer (id)
);

INSERT INTO food_delivery_db.cart_item (id, quantity, customer_id, food_id) VALUES (13, 1, 21, 12);
INSERT INTO food_delivery_db.cart_item (id, quantity, customer_id, food_id) VALUES (14, 4, 21, 10);
INSERT INTO food_delivery_db.cart_item (id, quantity, customer_id, food_id) VALUES (15, 2, 21, 11);
INSERT INTO food_delivery_db.cart_item (id, quantity, customer_id, food_id) VALUES (22, 2, 40, 2);
INSERT INTO food_delivery_db.cart_item (id, quantity, customer_id, food_id) VALUES (48, 1, 18, 1);