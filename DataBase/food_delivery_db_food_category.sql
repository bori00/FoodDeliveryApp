create table food_category
(
    id   bigint auto_increment
        primary key,
    name varchar(50) not null,
    constraint UK_m50tuveb7r41ja4ik00f8s10x
        unique (name)
);

INSERT INTO food_delivery_db.food_category (id, name) VALUES (2, 'appetizer');
INSERT INTO food_delivery_db.food_category (id, name) VALUES (6, 'beverage');
INSERT INTO food_delivery_db.food_category (id, name) VALUES (1, 'breakfast');
INSERT INTO food_delivery_db.food_category (id, name) VALUES (5, 'dessert');
INSERT INTO food_delivery_db.food_category (id, name) VALUES (3, 'main course');
INSERT INTO food_delivery_db.food_category (id, name) VALUES (4, 'soup');