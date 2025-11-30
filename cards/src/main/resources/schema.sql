CREATE TABLE IF NOT EXISTS `cards` (
    `card_id` VARCHAR(100) NOT NULL,
    `customer_mobile_number` VARCHAR(20) NOT NULL,
    `card_number` VARCHAR(100) NOT NULL,
    `card_type` VARCHAR(50) NOT NULL,
    `total_limit` BIGINT NOT NULL,
    `amount_used` BIGINT NOT NULL,
    `available_amount` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `updated_by` VARCHAR(20) DEFAULT NULL,
    PRIMARY KEY (`card_id`)
);
