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
(1, 'admin', 'Admin'),
(2, 'user', 'User'),
(3, 'librarian', 'Librarian')
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name);

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
('00000000-0000-0000-0000-000000000001', 'john', 'doe', 'john@example.com', '1234', 1)
ON DUPLICATE KEY UPDATE email = VALUES(email);

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
(3, '1984', 'George Orwell', '978-0451524935', 1949, 1)
ON DUPLICATE KEY UPDATE title = VALUES(title);

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

-- Example reserve (commented out) - uncomment to seed
-- INSERT INTO `reserves` (`reserve_date`, `status`, `book_id`, `user_id`) VALUES ('2025-12-12', 'ACTIVE', 1, '00000000-0000-0000-0000-000000000001');
