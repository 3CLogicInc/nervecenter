-- auto-generated definition
create table portal_menu
(
    id           int        null,
    name         mediumtext null,
    display_name mediumtext null,
    description  text       null,
    menu_type    mediumtext null,
    external     bit        null,
    link         text       null,
    parent       int        null,
    menu_order   int        null,
    active       bit        null,
    icon         mediumtext null,
    style        mediumtext null,
    access       int        null,
    created_at   timestamp  null,
    updated_at   timestamp  null,
    created_by   int        null,
    updated_by   int        null
);


alter table portal_menu
    add primary key (id);


-- Create view table
-- synergy_view

create table if not exists synergy_view (
                                            id int primary key,
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
    view_id int not null,
    association_grid_id int not null,
    created_at int default CURRENT_TIMESTAMP not null,
    constraint synergy_view_association_grid_synergy_view_id_fk
        foreign key (view_id) references synergy_view (id),
    constraint synergy_view_association_grid_synergy_view_id_fk_2
        foreign key (association_grid_id) references synergy_view (id)
);



create table synergy_user_view
(
    id int auto_increment,
    user_id int not null,
    view_id int not null,
    association_id int null,
    filters_config json null,
    projection_config json null,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    constraint synergy_user_view_pk
        primary key (id),
    constraint synergy_user_view_synergy_view_id_fk
        foreign key (view_id) references synergy_view (id),
    constraint synergy_user_view_synergy_view_id_fk_2
        foreign key (association_id) references synergy_view (id)
);

create index synergy_user_view_user_id_view_id_index
	on synergy_user_view (user_id, view_id);



create table synergy_user_view_customizations
(
    id int auto_increment,
    user_id int not null,
    view_id int not null,
    rules json null,
    actions json not null,
    action_type varchar(255) not null,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    constraint synergy_user_view_customizations_pk
        primary key (id),
    constraint synergy_user_view_customizations_synergy_view_id_fk
        foreign key (view_id) references synergy_view (id)
);

create index synergy_user_view_customizations_user_id_view_id_index
	on synergy_user_view_customizations (user_id, view_id);
