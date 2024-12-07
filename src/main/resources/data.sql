-- Insercion de usuarios iniciales
INSERT INTO USERS_APP (username, password, role, active, created_at, updated_at) 
VALUES 
('user1', '$2a$10$r/hTrlxQtfT.eAvPApFqIuNmbDqgGw9OqIBzHPe3MzX5kZzVsECYG', 'USER', true, '2023-12-01T10:00:00', '2023-12-01T10:00:00'),
('admin', '$2a$10$4H3t/eAsxJYmWhSoWD8dyOj6QKiRCQ5yDBnqDlKdxZ8QDvPfdu5pm', 'ADMIN', true, '2023-12-01T11:00:00', '2023-12-01T11:00:00');
-- user1:password123
-- admin:clave123

