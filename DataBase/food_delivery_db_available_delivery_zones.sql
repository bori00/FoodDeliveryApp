create table available_delivery_zones
(
    restaurant_id    bigint not null,
    delivery_zone_id bigint not null,
    primary key (restaurant_id, delivery_zone_id),
    constraint FKepvs5c3muec5n1ncu0cl0679v
        foreign key (restaurant_id) references restaurant (id),
    constraint FKr8tibxiu20yx7oyll8x1n1d18
        foreign key (delivery_zone_id) references delivery_zone (id)
);

INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (6, 2);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (7, 2);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (13, 2);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (6, 3);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (12, 3);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (13, 3);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (15, 3);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (1, 4);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (4, 4);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (13, 4);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (14, 4);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (15, 4);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (5, 6);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (6, 6);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (7, 6);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (8, 6);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (10, 6);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (11, 6);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (12, 6);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (13, 6);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (12, 7);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (13, 7);
INSERT INTO food_delivery_db.available_delivery_zones (restaurant_id, delivery_zone_id) VALUES (15, 7);