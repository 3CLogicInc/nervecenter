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

