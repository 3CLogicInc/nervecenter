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

