DELETE FROM section_items;
DELETE FROM order_items;
DELETE FROM sections;
DELETE FROM ordes;
DELETE FROM customers;
DELETE FROM storage;
DELETE FROM products;

INSERT INTO storage(name, capacity) VALUES
('storage1', 1000);

INSERT INTO sections(name, storage_id) VALUES
('fruits', 1),
('vegetables', 1),
('furniture', 1);

INSERT INTO products(name, weight, price) VALUES
('apple', 50, 10),
('pear', 60, 10),
('cucumber', 40, 15),
('tomato', 50, 20),
('sofa', 100000, 15000),
('chair' 5000, 400);

INSERT INTO section_items(product_id, amount, section_id) VALUES
(1, 100, 1),
(2, 75, 1),
(3, 50, 2),
(4, 50, 2),
(5, 3, 3),
(6, 10, 3);