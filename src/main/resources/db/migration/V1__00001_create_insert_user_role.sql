CREATE TABLE `accounts` (
    `id` BIGINT NOT NULL,
    `created_at` datetime NOT NULL,
    `created_by` BIGINT NOT NULL,
    `updated_at` datetime NOT NULL,
    `updated_by` BIGINT NOT NULL,
    `version` int DEFAULT NULL,
    `name` varchar(128) NOT NULL,
    `email` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_users_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `accounts`(`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`, `email`) VALUES(7154825306532495360, NOW(), -1, NOW(), -1, 1, 'test-admin', 'shubhamv.test@gmail.com');

CREATE TABLE `roles` (
  `id` BIGINT NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` BIGINT NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_by` BIGINT NOT NULL,
  `version` int DEFAULT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_roles_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `roles`(`id`,`created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`) VALUES(7154825376292159488, NOW(), (SELECT `id` FROM `accounts` WHERE `email` = 'shubhamv.test@gmail.com'), NOW(), (SELECT `id` FROM `accounts` WHERE `email` = 'shubhamv.test@gmail.com'), 1, 'ADMIN');
INSERT INTO `roles`(`id`,`created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`) VALUES(7154825673383100416, NOW(), (SELECT `id` FROM `accounts` WHERE `email` = 'shubhamv.test@gmail.com'), NOW(), (SELECT `id` FROM `accounts` WHERE `email` = 'shubhamv.test@gmail.com'), 1, 'USER');


CREATE TABLE `accounts_roles` (
  `id` BIGINT NOT NULL,
  `account_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `accounts_roles` (`id`,`account_id`, `role_id`) VALUES(7154825716202749952, (SELECT `id` FROM `accounts` WHERE `email` = 'shubhamv.test@gmail.com'), (SELECT `id` FROM `roles` WHERE `name` = 'ADMIN'));
