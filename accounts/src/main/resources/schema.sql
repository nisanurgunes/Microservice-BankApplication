CREATE TABLE IF NOT EXISTS `customer` (
                                          `customer_id` VARCHAR(100)  PRIMARY KEY,
                                          `customer_name` varchar(100) NOT NULL,
    `customer_email` varchar(100) NOT NULL,
    `customer_mobile_number` varchar(20) NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
    );

CREATE TABLE IF NOT EXISTS `accounts` (
`accounts_id` VARCHAR(100)  PRIMARY KEY,
                                          `customer_id` VARCHAR(100) NOT NULL,
                                          `account_number` VARCHAR(20) NOT NULL,
                                          `account_type` varchar(100) NOT NULL,
    `branch_address` varchar(200) DEFAULT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
    );