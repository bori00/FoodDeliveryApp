create table restaurant
(
    id       bigint auto_increment
        primary key,
    address  varchar(500) not null,
    name     varchar(50)  not null,
    admin_id bigint       null,
    constraint UK_i6u3x7opncroyhd755ejknses
        unique (name),
    constraint FKn1h9k9ftcn9y8wsf6ctwbag8p
        foreign key (admin_id) references admin (id)
);

INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (1, 'Main Street', 'Main Restaurnat', 11);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (4, 'Main Street', 'Main Restaurant 2', 12);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (5, 'Main Street', 'Main Restaurant 3', 13);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (6, 'Main Street', 'Main Restaurant 4', 14);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (7, 'Main Street', 'Main Restaurant 5', 15);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (8, 'Cluj6', 'Rest6', 33);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (10, 'Cluj7', 'Rest7', 34);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (11, 'Cluj8', 'Rest8', 35);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (12, 'Cluj9', 'Rest9', 36);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (13, 'Cluj10', 'Rest10', 37);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (14, 'Cluj11', 'Rest11', 38);
INSERT INTO food_delivery_db.restaurant (id, address, name, admin_id) VALUES (15, 'Cluj12', 'Rest12', 39);