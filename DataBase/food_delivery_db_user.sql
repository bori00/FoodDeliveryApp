create table user
(
    id        bigint auto_increment
        primary key,
    password  varchar(100) not null,
    user_name varchar(30)  not null,
    constraint UK_lqjrcobrh9jc8wpcar64q1bfh
        unique (user_name)
);

INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (10, '$2a$10$FTtlzTXQqdBV27otl3HceOR3Wt8a0TZs4C.xw2uKp/yAoqCAILT2C', 'bori');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (11, '$2a$10$qEmU2jEqz1/3d8JzpmePuef16MxlWFeJQ9gOzuRS6E/P7jmnVooy2', 'admin');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (12, '$2a$10$dRAEq9rCOh9nRggC8ygUau.F7cw1IdpTx58LnKPl9m6JszCIZCpUS', 'admin2');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (13, '$2a$10$OfuR6VRxHtA7Rebzpwaw6umQkq7XPEGG0B1qmP.LH5xtbpBjDaCd.', 'admin3');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (14, '$2a$10$JMRdYXZXZsIarzYQpc6/JuicCxfF56yFkUt7cr4sjGzQHSZfRJ5zy', 'admin4');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (15, '$2a$10$4bAycMk8oKQ5VLmd8fov8u2RYd7t4HK1.TGI1Im9Qx2hs7j5s/33G', 'admin5');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (16, '$2a$10$pnvY4X/MHKpe0a2Nl6Ff9Orf9pnGetR/gMwu3M4gE1iOFS.KY0R4u', 'anna');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (17, '$2a$10$u4Poum3tdGS896BjvGlmh.1q6cErXZiY8tfY.qPNIc50hX5pf30eC', 'peter');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (18, '$2a$10$KyfyM7ZBQdpGZ/MryoupieJ/iMgoaQNbOUuFCLKOdvFYHiBEiFV6K', 'anna2');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (19, '$2a$10$wqEc4baoCiUdkOWgZJqHzuQ13Xkl/0JjvWJ2tvuPKQhWJ0QDWyMR6', 'anna3');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (20, '$2a$10$/PRagHiSKb5n6lnqnn6zKOKIbYMsWdsL.QtiEGTeUHhqdHmB8O0EC', 'anna5');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (21, '$2a$10$xU1aOwriSvWNnFxbK7DS9uOPh578B5X1XEwV.MYvv7EpFA342CU6W', 'anna6');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (22, '$2a$10$EzITVBqU2R0ZhRBlKHqKDOxiJUAOzzMOkbeIJKvwN7Ose4x1BmNIS', 'anna7');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (23, '$2a$10$V/889vb4Bp/TCkEARI0q0uVV6bwtXYhn0n4zhwnq8AlhRTZg.Z0Qa', 'anna8');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (24, '$2a$10$9Uk4enRCXRlYcfTR57wmcO2bLZxJKjiuqKHm2heSQbRJO5cFyx936', 'anna9');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (25, '$2a$10$aCHTOYGEbyZfllNNHEvy/eTMHPGSmlJvV8ERb1h09yUFyx.h0n1P.', 'anna10');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (26, '$2a$10$OsIVctlrqFRgORtd.0g7oefQd3/97kR.PW2/llzUMGEoaGcQekmeq', 'anna11');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (27, '$2a$10$KI5ndVDmaiKoqvod7eaLHO7fygselS0LfWUzf3NRROXrp0rZhgosu', 'anna12');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (28, '$2a$10$./Ulyc485uuhTZVvwpGdtuuQokL.jMlpesyTyHMB14R/l3uJd79q6', 'anna13');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (29, '$2a$10$monQSNAW4SkaB4NlX42DOOJ6zdoj9yNKdMV/kq/ovRzrMYvAhJFGq', 'anna15');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (30, '$2a$10$p39ezmz6y9WXkXVUHp8oGe0M3ujkHRx817epOBKG8wxwPzAzozEnW', 'anna16');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (31, '$2a$10$BQy8LSuAE6Sjh6jCyHjQ4O4C/uIi4h8RgJtKeMinVXT2OLRcZEdva', 'anna17');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (32, '$2a$10$FS8Me7HpHWRtzjSqUa.lV.SdgSwH/ltqNiXkOi6hsP85LAMHhih2a', 'anna18');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (33, '$2a$10$B6YKIlCh9PakQo4aSuPPK.Q4ZK3.5.bAlIx7IeMjlM7LZzRUC3dQ.', 'admin6');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (34, '$2a$10$X399oxbz6WCH1jxH9aytbej44zraGKsIv3vyowtQjLGPnvch1r9KO', 'admin7');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (35, '$2a$10$fu.sADYs4GiAwN65Bd6VaOZSVZbrOYGAdQrVF9DLEnE2wco97QQRS', 'admin8');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (36, '$2a$10$bYAvW8hQHBTPO7uuXx/Uku9D1/15xIGgWCUxfaH0S2gNdCQcAYA1G', 'admin9');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (37, '$2a$10$jzN2BMiAgq7JGwJpdHvbdeoTcwMlAfYiSZRhHJSbFaFnLgQjznp6G', 'admin10');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (38, '$2a$10$G43wCFy7oWkZZwMHjuHDBOKHofMwzouXYQotnpscgMXhuwr.2Vw46', 'admin11');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (39, '$2a$10$qt./.H7k83OdDH8NI7VhAe/40oaOABfTFNE7lrSBKhTVze1thgiii', 'admin12');
INSERT INTO food_delivery_db.user (id, password, user_name) VALUES (40, '$2a$10$D81lauDCeQLs.bg7HoKjRel1boruChj4UNxc5ZuQQYPPkFnxrA/C6', 'simon');