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
