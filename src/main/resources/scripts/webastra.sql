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

INSERT INTO `webastra`.`role` (id, name, heirarchy) values (1, "3C_ADMIN", 1);
INSERT INTO `webastra`.`role` (id, name, heirarchy) values (2, "CC_ADMIN", 2);
INSERT INTO `webastra`.`role` (id, name, heirarchy) values (3, "SUPERVISOR", 3);
INSERT INTO `webastra`.`role` (id, name, heirarchy) values (4, "AGENT", 4);

INSERT INTO `webastra`.permission (id, permission_group, permission) values (1, "user", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (2, "user", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (3, "user", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (4, "user", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (5, "account_profile", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (6, "account_profile", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (7, "account_profile", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (8, "account_profile", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (9, "skill", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (10, "skill", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (11, "skill", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (12, "skill", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (13, "queue", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (14, "queue", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (15, "queue", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (16, "queue", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (17, "interaction_flow", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (18, "interaction_flow", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (19, "interaction_flow", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (20, "interaction_flow", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (21, "activity_logs", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (22, "activity_logs", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (23, "activity_logs", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (24, "activity_logs", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (25, "cti_profile", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (26, "cti_profile", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (27, "cti_profile", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (28, "cti_profile", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (29, "presence", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (30, "presence", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (31, "presence", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (32, "presence", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (33, "adapter", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (34, "adapter", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (35, "adapter", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (36, "adapter", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (37, "webhook", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (38, "webhook", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (39, "webhook", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (40, "webhook", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (41, "phonebook", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (42, "phonebook", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (43, "phonebook", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (44, "phonebook", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (45, "external_contact", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (46, "external_contact", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (47, "external_contact", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (48, "external_contact", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (49, "caller_id_policy", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (50, "caller_id_policy", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (51, "caller_id_policy", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (52, "caller_id_policy", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (53, "disposition_code", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (54, "disposition_code", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (55, "disposition_code", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (56, "disposition_code", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (57, "disposition_code_group", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (58, "disposition_code_group", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (59, "disposition_code_group", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (60, "disposition_code_group", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (61, "recording_policy", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (62, "recording_policy", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (63, "recording_policy", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (64, "recording_policy", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (69, "voice_recording_policy", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (70, "voice_recording_policy", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (71, "voice_recording_policy", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (72, "voice_recording_policy", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (73, "route", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (74, "route", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (75, "route", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (76, "route", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (77, "alert_tones", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (78, "alert_tones", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (79, "alert_tones", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (80, "alert_tones", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (81, "media_prompts", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (82, "media_prompts", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (83, "media_prompts", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (84, "media_prompts", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (85, "roles_and_permissions", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (86, "roles_and_permissions", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (87, "roles_and_permissions", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (88, "roles_and_permissions", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (89, "dashboard", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (90, "dashboard", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (91, "dashboard", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (92, "dashboard", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (93, "reporting", "create");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (94, "reporting", "read");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (95, "reporting", "update");
INSERT INTO `webastra`.permission (id, permission_group, permission) values (96, "reporting", "delete");

INSERT INTO `webastra`.permission (id, permission_group, permission) values (97, "cti", "view");

INSERT INTO `webastra`.role_permission (role_id, permission_id) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(1,46),(1,47),(1,48),(1,49),(1,50),(1,51),(1,52),(1,53),(1,54),(1,55),(1,56),(1,57),(1,58),(1,59),(1,60),(1,61),(1,62),(1,63),(1,64),(1,65),(1,66),(1,67),(1,68),(1,69),(1,70),(1,71),(1,72),(1,73),(1,74),(1,75),(1,76),(1,77),(1,78),(1,79),(1,80),(1,81),(1,82),(1,83),(1,84),(1,85),(1,86),(1,87),(1,88),(1,89),(1,90),(1,91),(1,92),(1,93),(1,94),(1,95),(1,96),(1,97);
INSERT INTO `webastra`.role_permission (role_id, permission_id) values (2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(2,11),(2,12),(2,13),(2,14),(2,15),(2,16),(2,17),(2,18),(2,19),(2,20),(2,21),(2,22),(2,23),(2,24),(2,25),(2,26),(2,27),(2,28),(2,29),(2,30),(2,31),(2,32),(2,33),(2,34),(2,35),(2,36),(2,37),(2,38),(2,39),(2,40),(2,41),(2,42),(2,43),(2,44),(2,45),(2,46),(2,47),(2,48),(2,49),(2,50),(2,51),(2,52),(2,53),(2,54),(2,55),(2,56),(2,57),(2,58),(2,59),(2,60),(2,61),(2,62),(2,63),(2,64),(2,65),(2,66),(2,67),(2,68),(2,69),(2,70),(2,71),(2,72),(2,73),(2,74),(2,75),(2,76),(2,77),(2,78),(2,79),(2,80),(2,81),(2,82),(2,83),(2,84),(2,85),(2,86),(2,87),(2,88),(2,89),(2,90),(2,91),(2,92),(2,93),(2,94),(2,95),(2,96),(2,97);
INSERT INTO `webastra`.role_permission (role_id, permission_id) values (3,1),(3,2),(3,3),(3,5),(3,6),(3,7),(3,9),(3,10),(3,11),(3,13),(3,14),(3,15),(3,17),(3,18),(3,19),(3,21),(3,22),(3,23),(3,25),(3,26),(3,27),(3,29),(3,30),(3,31),(3,33),(3,34),(3,35),(3,37),(3,38),(3,39),(3,41),(3,42),(3,43),(3,45),(3,46),(3,47),(3,49),(3,50),(3,51),(3,53),(3,54),(3,55),(3,57),(3,58),(3,59),(3,61),(3,62),(3,63),(3,65),(3,66),(3,67),(3,69),(3,70),(3,71),(3,73),(3,74),(3,75),(3,77),(3,78),(3,79),(3,81),(3,82),(3,83),(3,85),(3,86),(3,87),(3,89),(3,90),(3,91),(3,93),(3,94),(3,95),(3,97);
INSERT INTO `webastra`.role_permission (role_id, permission_id) values (4,2),(4,97);

INSERT INTO `webastra`.user_role (user_id, role_id) values (1,1);