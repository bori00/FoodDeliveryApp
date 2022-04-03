create table delivery_zone
(
    id   bigint auto_increment
        primary key,
    name varchar(50) not null,
    constraint UK_ss6fqqek7kf2owkosw5ccnk1y
        unique (name)
);

INSERT INTO food_delivery_db.delivery_zone (id, name) VALUES (6, 'Gheorgheni');
INSERT INTO food_delivery_db.delivery_zone (id, name) VALUES (4, 'Grigorescu');
INSERT INTO food_delivery_db.delivery_zone (id, name) VALUES (2, 'Manastur');
INSERT INTO food_delivery_db.delivery_zone (id, name) VALUES (3, 'Marasti');
INSERT INTO food_delivery_db.delivery_zone (id, name) VALUES (7, 'Zorilor');