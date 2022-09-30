create table properties
(
    id int,
    key varchar(256) null,
    value text null,
    load_on int null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP null
);

create unique index properties_id_uindex
    on properties (id);

create unique index properties_key_uindex
    on properties (`key`);

alter table properties
    add constraint properties_pk
        primary key (id);

alter table properties modify id int auto_increment;

--  Activity Logs

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

INSERT INTO properties
(`key`, value, load_on)
VALUES
('signout_url', 'https://portal20.i3clogic.com/signout', 1),
('ls_url', 'https://gateway.i3clogic.com', 1),
('secret_key_aggrid','jkashdfklashdfbnmb,zx.vznlozshrweermhwkzbvkKSDHI#Wk', 1),
('i18n','https://dev-testing-new.s3.amazonaws.com/i18n/en/i18n.js?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220831T151549Z&X-Amz-SignedHeaders=host&X-Amz-Expires=900&X-Amz-Credential=AKIAIPDCMYBWQ6I5W5JQ%2F20220831%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=8c7b0d1d6e45ca77fafbcfa21aee844ae12dc6f008d01d8d955bbd3659a87e3b', 1),
('domain_gateway','https://gateway.i3clogic.com', 1),
('footer_privacy_url','https://www.3clogic.com/privacy-policy', 1),
('footer_contact_url','https://www.3clogic.com/contact-us/', 1),
('footer_terms_url','https://www.3clogic.com/terms-of-use', 1),
('footer_copyright_url','https://www.3clogic.com/privacy-policy', 1),
('footer_aboutus_url','https://www.3clogic.com/product/', 1);


-- Create view table
-- synergy_view

create table if not exists synergy_view (
    id serial primary key,
    header json,
    body json not null ,
    filters json,
    projections json,
    secondary boolean,
    created_at timestamp not null default CURRENT_TIMESTAMP,
    updated_at timestamp not null default CURRENT_TIMESTAMP
);

create table synergy_view_association_grid
(
    view_id integer
        constraint synergy_view_association_grid_synergy_view_id_fk
        references synergy_view (id),
    association_grid_id integer
        constraint synergy_view_association_grid_view_id___fk
        references synergy_view (id),
    created_at timestamp default CURRENT_TIMESTAMP
);


create table synergy_user_view
(
    id serial primary key,
    user_id int,
    view_id int
        constraint synergy_user_view_synergy_view_id_fk
        references synergy_view (id),
    association_id int
        constraint synergy_user_view__association_view_fk
        references synergy_view (id),
    filters_config json,
    projection_config json,
    created_at timestamp default current_timestamp
);

create table synergy_user_view_customizations
(
    user_id int not null,
    view_id int not null
        constraint synergy_user_view_customizations_synergy_view_id_fk
        references synergy_view (id),
    rules json not null,
    actions json not null,
    action_type varchar(255) not null,
    created_at timestamp default current_timestamp
);

