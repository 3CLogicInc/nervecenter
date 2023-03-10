create table nc_routes
(
    id int auto_increment,
    name varchar(255) null,
    domain varchar(512) null,
    is_default boolean default false null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP() null,
    created_by int null,
    updated_by int null,
    constraint nc_routes_pk
        primary key (id)
);

create table webastra.nc_route_exceptions
(
    exception_id int not null,
    route_id int not null,
    domain varchar(512) null,
    prefix int null,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    updated_at timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    created_by int null,
    updated_by int null,
    primary key (exception_id, route_id)
);

create table nc_callcenter_routes
(
    callcenter_id int not null,
    route_id int not null,
    created_at timestamp default current_timestamp null,
    created_by int null,
    constraint nc_callcenter_routes_pk
        unique (callcenter_id, route_id)
);

