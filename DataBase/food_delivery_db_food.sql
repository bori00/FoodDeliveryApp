create table food
(
    id               bigint auto_increment
        primary key,
    name             varchar(50)  not null,
    food_category_id bigint       not null,
    restaurant_id    bigint       not null,
    price            double       not null,
    description      varchar(200) not null,
    constraint UK_qkhr2yo38c1g9n5ss0jl7gxk6
        unique (name),
    constraint FKm9xrxt95wwp1r2s7andom1l1c
        foreign key (restaurant_id) references restaurant (id),
    constraint FKpnfa6f8ubf600psx2mhefa1a1
        foreign key (food_category_id) references food_category (id)
);

INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (1, 'spaghetti', 4, 6, 25.5, 'originale di Milano');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (2, 'spaghetti supremo', 2, 6, 35.5, 'originale di Milano');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (3, 'spaghetti supremo Res5', 2, 5, 35.5, 'originale di Milano');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (4, 'spaghetti simple', 2, 7, 15.5, 'originale di Milano');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (5, 'pizza margherita', 3, 7, 18.5, 'originale di Napoli');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (6, 'pizza marinara', 3, 7, 18.5, 'originale di Napoli');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (7, 'gazpaccho', 4, 5, 12.5, 'hola!');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (9, 'gazpaccho3', 4, 7, 12.5, 'hola!');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (10, 'Rest12Soup1', 4, 15, 12.5, '');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (11, 'Rest12Breakfast1', 1, 15, 18, 'have a nice day!');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (12, 'Rest12Soup2', 4, 15, 13.9, 'second best');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (13, 'Rest10Beverage1', 6, 13, 11, 'hot drink');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (14, 'Risotto with Seafood', 3, 7, 35.6, 'italian');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (15, 'Red Wine (glass)', 6, 7, 5, '');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (16, 'Tiramisu', 5, 7, 12.8, '');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (17, 'Panna Cotta', 5, 7, 10.4, '');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (18, 'Frittata', 1, 7, 8, '');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (19, 'Cappuccino', 6, 7, 3.5, '');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (20, 'White Wine (glass)', 6, 7, 8.7, '');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (21, 'Rest12Drink1', 6, 15, 3.2, '');
INSERT INTO food_delivery_db.food (id, name, food_category_id, restaurant_id, price, description) VALUES (22, 'Sfogliatelle', 5, 7, 12.8, 'Can''t skip this');