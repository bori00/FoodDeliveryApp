create table customer
(
    id bigint not null
        primary key,
    constraint FKg2o3t8h0g17smtr9jgypagdtv
        foreign key (id) references user (id)
);

INSERT INTO food_delivery_db.customer (id) VALUES (10);
INSERT INTO food_delivery_db.customer (id) VALUES (16);
INSERT INTO food_delivery_db.customer (id) VALUES (17);
INSERT INTO food_delivery_db.customer (id) VALUES (18);
INSERT INTO food_delivery_db.customer (id) VALUES (19);
INSERT INTO food_delivery_db.customer (id) VALUES (20);
INSERT INTO food_delivery_db.customer (id) VALUES (21);
INSERT INTO food_delivery_db.customer (id) VALUES (22);
INSERT INTO food_delivery_db.customer (id) VALUES (23);
INSERT INTO food_delivery_db.customer (id) VALUES (24);
INSERT INTO food_delivery_db.customer (id) VALUES (25);
INSERT INTO food_delivery_db.customer (id) VALUES (26);
INSERT INTO food_delivery_db.customer (id) VALUES (27);
INSERT INTO food_delivery_db.customer (id) VALUES (28);
INSERT INTO food_delivery_db.customer (id) VALUES (30);
INSERT INTO food_delivery_db.customer (id) VALUES (40);