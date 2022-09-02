create table properties
(
    id int,
    `key` varchar(256) null,
    value text null,
    load_on int null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP null
)
    comment 'Properties Required to run Sailor';

create unique index properties_id_uindex
    on properties (id);

create unique index properties_key_uindex
    on properties (`key`);

alter table properties
    add constraint properties_pk
        primary key (id);

alter table properties modify id int auto_increment;

# Activity Logs

create table activity_log
(
    id int,
    activity_type varchar(512) not null,
    details text null,
    created_by int null,
    created_at int null
);

create unique index activity_log_id_uindex
    on activity_log (id);

alter table activity_log
    add constraint activity_log_pk
        primary key (id);

alter table activity_log modify id int auto_increment;


