-- Insercion de usuarios iniciales
INSERT INTO USERS_APP (username, password, role, active, created_at, updated_at) 
VALUES 
('user1', '$2a$10$r/hTrlxQtfT.eAvPApFqIuNmbDqgGw9OqIBzHPe3MzX5kZzVsECYG', 'USER', true, '2023-12-01T10:00:00', '2023-12-01T10:00:00'),
('admin', '$2a$10$4H3t/eAsxJYmWhSoWD8dyOj6QKiRCQ5yDBnqDlKdxZ8QDvPfdu5pm', 'ADMIN', true, '2023-12-01T11:00:00', '2023-12-01T11:00:00');
-- user1:password123
-- admin:clave123


-- Carga inicial de productos
INSERT INTO product (name, category, price) VALUES
('Laptop', 'Electronics', 999.99),
('Headphones', 'Electronics', 199.99),
('Smartphone', 'Electronics', 799.99),
('Tablet', 'Electronics', 499.99),
('Fiction Book', 'Books', 15.99),
('Cookbook', 'Books', 25.99),
('History Book', 'Books', 20.99),
('Biography', 'Books', 18.99),
('Desk', 'Furniture', 120.00),
('Chair', 'Furniture', 85.00),
('Bed', 'Furniture', 450.00),
('Sofa', 'Furniture', 750.00),
('T-Shirt', 'Clothing', 25.00),
('Jeans', 'Clothing', 60.00),
('Jacket', 'Clothing', 120.00),
('Shoes', 'Clothing', 80.00),
('Blender', 'Appliances', 35.00),
('Microwave', 'Appliances', 150.00),
('Refrigerator', 'Appliances', 899.99),
('Washing Machine', 'Appliances', 499.99);
