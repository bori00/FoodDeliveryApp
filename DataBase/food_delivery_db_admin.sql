create table admin
(
    id bigint not null
        primary key,
    constraint FK1ja8rua032fgnk9jmq7du3b3a
        foreign key (id) references user (id)
);

INSERT INTO food_delivery_db.admin (id) VALUES (11);
INSERT INTO food_delivery_db.admin (id) VALUES (12);
INSERT INTO food_delivery_db.admin (id) VALUES (13);
INSERT INTO food_delivery_db.admin (id) VALUES (14);
INSERT INTO food_delivery_db.admin (id) VALUES (15);
INSERT INTO food_delivery_db.admin (id) VALUES (29);
INSERT INTO food_delivery_db.admin (id) VALUES (31);
INSERT INTO food_delivery_db.admin (id) VALUES (32);
INSERT INTO food_delivery_db.admin (id) VALUES (33);
INSERT INTO food_delivery_db.admin (id) VALUES (34);
INSERT INTO food_delivery_db.admin (id) VALUES (35);
INSERT INTO food_delivery_db.admin (id) VALUES (36);
INSERT INTO food_delivery_db.admin (id) VALUES (37);
INSERT INTO food_delivery_db.admin (id) VALUES (38);
INSERT INTO food_delivery_db.admin (id) VALUES (39);