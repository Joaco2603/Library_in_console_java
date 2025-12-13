-- Init SQL for Library_in_console_java
-- Creates schema and seed data for MySQL

CREATE DATABASE IF NOT EXISTS `librarydb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `librarydb`;

-- Roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` INT NOT NULL PRIMARY KEY,
  `role_name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `roles` (`id`, `role_name`, `description`) VALUES
(1, 'admin', 'Administrator with full permissions'),
(2, 'member', 'Registered library member'),
(3, 'librarian', 'Library staff with management capabilities')
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name), description = VALUES(description);

-- Users
CREATE TABLE IF NOT EXISTS `users` (
  `id` VARCHAR(36) NOT NULL PRIMARY KEY,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(150) NOT NULL UNIQUE,
  `password` VARCHAR(100) NOT NULL,
  `role_id` INT,
  FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Default admin user (id fixed so Java code can reference or be independent)
INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`) VALUES
('00000000-0000-0000-0000-000000000001', 'John', 'Doe', 'john@example.com', '1234', 1),
('11111111-1111-1111-1111-111111111111', 'Maria', 'Gonzalez', 'maria@example.com', 'pass456', 3),
('22222222-2222-2222-2222-222222222222', 'Carlos', 'Rodriguez', 'carlos@example.com', 'pass789', 2)
ON DUPLICATE KEY UPDATE email = VALUES(email), first_name = VALUES(first_name), last_name = VALUES(last_name), role_id = VALUES(role_id);

-- Books
CREATE TABLE IF NOT EXISTS `books` (
  `id` INT NOT NULL PRIMARY KEY,
  `title` VARCHAR(255) NOT NULL,
  `author` VARCHAR(255) NOT NULL,
  `isbn` VARCHAR(50) NOT NULL UNIQUE,
  `year` INT,
  `available` TINYINT(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `books` (`id`, `title`, `author`, `isbn`, `year`, `available`) VALUES
(1, 'Cien años de soledad', 'Gabriel García Márquez', '978-0307474728', 1967, 1),
(2, 'Don Quijote de la Mancha', 'Miguel de Cervantes', '978-8424936464', 1605, 1),
(3, '1984', 'George Orwell', '978-0451524935', 1949, 1),
(4, 'El amor en los tiempos del cólera', 'Gabriel García Márquez', '978-0307387738', 1985, 0),
(5, 'Rayuela', 'Julio Cortázar', '978-8420633695', 1963, 1)
ON DUPLICATE KEY UPDATE title = VALUES(title), author = VALUES(author), year = VALUES(year), available = VALUES(available);

-- Reserves
CREATE TABLE IF NOT EXISTS `reserves` (
  `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `reserve_date` DATE,
  `status` VARCHAR(50),
  `book_id` INT,
  `user_id` VARCHAR(36),
  FOREIGN KEY (`book_id`) REFERENCES `books`(`id`) ON DELETE SET NULL,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Seed reserves
INSERT INTO `reserves` (`reserve_date`, `status`, `book_id`, `user_id`) VALUES
('2025-12-01', 'active', 1, '22222222-2222-2222-2222-222222222222'),
('2025-12-05', 'active', 3, '11111111-1111-1111-1111-111111111111'),
('2025-12-08', 'completed', 5, '22222222-2222-2222-2222-222222222222')
ON DUPLICATE KEY UPDATE reserve_date = VALUES(reserve_date), status = VALUES(status), book_id = VALUES(book_id), user_id = VALUES(user_id);
