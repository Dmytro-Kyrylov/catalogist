create sequence seq_user;
create sequence seq_list;
create sequence seq_list_row;

create table "user"
(
    id       bigint
        constraint user_pk primary key,
    created_at  timestamp not null default current_timestamp,
    username text not null,
    password text not null,
    unique (username)
);

create table list
(
    id          bigint
        constraint list_pk primary key,
    created_by  bigint    not null
        constraint list_user_create_fk references "user",
    created_at  timestamp not null default current_timestamp,
    updated_by  bigint    not null
        constraint list_user_update_fk references "user",
    updated_at  timestamp not null default current_timestamp,
    title       text      not null,
    description text
);

create table list_user
(
    list_id bigint not null
        constraint list_user_list_fk references list,
    user_id bigint not null
        constraint list_user_user_fk references "user",
    constraint list_user_pk primary key (list_id, user_id)
);

create table list_row
(
    id         bigint
        constraint list_row_pk primary key,
    created_by bigint    not null
        constraint list_row_user_create_fk references "user",
    created_at timestamp not null default current_timestamp,
    updated_by bigint    not null
        constraint list_row_user_update_fk references "user",
    updated_at timestamp not null default current_timestamp,
    list_id    bigint    not null
        constraint list_row_list_fk references list,
    title      text      not null,
    content    text,
    links      text[]
);

