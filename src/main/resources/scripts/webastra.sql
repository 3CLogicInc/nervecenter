CREATE TABLE `nc_routes` (
                             `id` int unsigned NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
                             `domain` int DEFAULT NULL,
                             `is_default` tinyint(1) DEFAULT '0',
                             `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                             `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             `created_by` int unsigned DEFAULT NULL,
                             `updated_by` int unsigned DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table webastra.nc_route_exceptions
(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    route_id int UNSIGNED not null,
    country varchar(64) null,
    prefix int null,
    exception_domain_id int null,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    updated_at timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    created_by int UNSIGNED null,
    updated_by int UNSIGNED null,
    primary key (id),
    unique key (id, route_id)
);

create table webastra.nc_callcenter_routes
(
    callcenter_id int UNSIGNED not null,
    route_id int UNSIGNED not null,
    created_at timestamp default current_timestamp null,
    created_by int null,
    constraint nc_callcenter_routes_pk
        unique (callcenter_id, route_id)
);

CREATE TABLE webastra.`nc_domains` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `domain` varchar(256) DEFAULT NULL,
                              `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE IF NOT EXISTS `!-TENANT-!`.`role` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(256) CHARACTER SET 'utf8mb4' NOT NULL DEFAULT '',
	`description` text CHARACTER SET 'utf8mb4',
	`hierarchy` INT(10) NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` INT UNSIGNED NULL DEFAULT NULL,
    `updated_by` INT UNSIGNED NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_name (`name`)
)
ENGINE = InnoDB DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `!-TENANT-!`.`permission` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `permission_group` VARCHAR(256) CHARACTER SET 'utf8mb4' NOT NULL DEFAULT '',
	`name` VARCHAR(256) CHARACTER SET 'utf8mb4' NOT NULL DEFAULT '',
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` INT UNSIGNED NULL DEFAULT NULL,
    `updated_by` INT UNSIGNED NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_name (`name`)
)
ENGINE = InnoDB DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `!-TENANT-!`.`role_permission` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `role_id` INT UNSIGNED NOT NULL,
	`permission_id` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` INT UNSIGNED NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_role_id_permission_id (`role_id`,`permission_id`),
	CONSTRAINT `role_permission_fk_1` FOREIGN KEY (`role_id`) REFERENCES `!-TENANT-!`.`role` (`id`),
	CONSTRAINT `role_permission_fk_2` FOREIGN KEY (`permission_id`) REFERENCES `!-TENANT-!`.`permission` (`id`)
)
ENGINE = InnoDB DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `!-TENANT-!`.`user_role` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` INT UNSIGNED NOT NULL,
	`role_id` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` INT UNSIGNED NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_user_id_role_id (`user_id`, `role_id`),
	CONSTRAINT `user_role_fk_1` FOREIGN KEY (`user_id`) REFERENCES `!-TENANT-!`.`user` (`id`),
	CONSTRAINT `user_role_fk_2` FOREIGN KEY (`role_id`) REFERENCES `!-TENANT-!`.`role` (`id`)
)
ENGINE = InnoDB DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `!-TENANT-!`.`license` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(256) CHARACTER SET 'utf8mb4' NOT NULL,
	`type` VARCHAR(256) CHARACTER SET 'utf8mb4' NOT NULL,
	`quantity` INT UNSIGNED NOT NULL,
	`start_date_time` INT UNSIGNED NOT NULL,
	`end_date_time` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` INT UNSIGNED NULL DEFAULT NULL,
    `updated_by` INT UNSIGNED NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_name (`name`)
)
ENGINE = InnoDB DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `!-TENANT-!`.`license_permission` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `license_id` INT UNSIGNED NOT NULL,
	`permission_id` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` INT UNSIGNED NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_license_id_permission_id (`license_id`, `permission_id`),
	CONSTRAINT `license_permission_fk_1` FOREIGN KEY (`license_id`) REFERENCES `!-TENANT-!`.`license` (`id`),
	CONSTRAINT `license_permission_fk_2` FOREIGN KEY (`permission_id`) REFERENCES `!-TENANT-!`.`permission` (`id`)
)
ENGINE = InnoDB DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `!-TENANT-!`.`account_license` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`account_id` INT UNSIGNED NOT NULL,
    `license_id` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` INT UNSIGNED NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_account_id_license_id (`account_id`, `license_id`),
	CONSTRAINT `account_license_fk_2` FOREIGN KEY (`account_id`) REFERENCES `!-TENANT-!`.`accounts` (`id`),
	CONSTRAINT `account_license_fk_1` FOREIGN KEY (`license_id`) REFERENCES `!-TENANT-!`.`license` (`id`)
)
ENGINE = InnoDB DEFAULT CHARACTER SET = latin1;