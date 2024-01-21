CREATE TABLE `locks` (
    `id` BIGINT NOT NULL,
    `name` VARCHAR(64) NOT NULL,
    `version` INT NOT NULL DEFAULT 0,
    `owner` VARCHAR(40),
    `expiry_at` TIMESTAMP,
    PRIMARY KEY(`id`),
    UNIQUE KEY(`name`)
);