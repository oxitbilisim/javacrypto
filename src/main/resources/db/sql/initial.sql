create table confirmation_token
(
    token_id           bigint not null
        constraint confirmation_token_pkey
            primary key,
    confirmation_token text,
    created_date       timestamp,
    user_id            bigint
);

alter table confirmation_token
    owner to postgres;


create table password_reset_token
(
    id           bigint not null
        constraint password_reset_token_pkey
            primary key,
    token        text,
    user_id      bigint,
    expiry_date  timestamp,
    created_date timestamp
);

alter table password_reset_token
    owner to postgres;

create table s_role
(
    id        bigint not null
        constraint role_pkey
            primary key,
    role_name text
);

alter table s_role
    owner to postgres;

create table s_user
(
    id         bigint not null
        constraint "User_pkey"
            primary key,
    email      text,
    password   text,
    first_name text,
    last_name  text,
    is_enabled boolean
);

alter table s_user
    owner to postgres;

create table s_user_role
(
    user_id bigint
        constraint s_user_role_s_user_id_fk
            references s_user,
    role_id bigint
        constraint s_user_role_s_role_id_fk
            references s_role
);

alter table s_user_role
    owner to postgres;

create table s_permission
(
    id         bigint not null
        constraint s_permission_pkey
            primary key
        constraint s_permission_id_key
            unique,
    parent_id  bigint
        constraint fk_permission_permission
            references s_permission,
    name       varchar(100),
    perm_level bigint,
    title      varchar(100)
);

alter table s_permission
    owner to postgres;

create table s_role_permission
(
    role_id       bigint
        constraint fk_role_permission_role
            references s_role,
    permission_id bigint
        constraint fk_role_permission_permission
            references s_permission,
    constraint s_role_permission_role_id_permission_id_key
        unique (role_id, permission_id)
);

alter table s_role_permission
    owner to postgres;


create table s_menu
(
    id            bigint not null
        constraint s_menu_pkey
            primary key
        constraint s_menu_id_key
            unique,
    parent_id     bigint
        constraint fk_menu_menu
            references s_menu
        constraint fk_menu_permission
            references s_permission,
    title         varchar(100),
    page          varchar(100),
    icon          varchar(100),
    active        boolean,
    row_num       integer,
    permission_id bigint
);

alter table s_menu
    owner to postgres;



create table email_templates
(
    id      bigint not null
        constraint email_templates_pkey
            primary key,
    key     text,
    subject jsonb,
    message jsonb,
    note    text
);

alter table email_templates
    owner to postgres;

create table s_user_profile
(
    id                       bigint not null
        constraint s_user_profile_pkey
            primary key,
    s_user_id                bigint,
    company_name             varchar(250),
    user_locale                 varchar(50),
    country_code             varchar(50),
    phone                    varchar(50)
);

alter table s_user_profile
    owner to postgres;

create table directories
(
    id        bigint not null
        constraint directories_pk
            primary key,
    parent_id bigint,
    name      text
);

alter table directories
    owner to postgres;

create table upload
(
    id           bigint not null
        constraint upload_pk
            primary key,
    directory_id bigint
        constraint upload_directories_id_fk
            references directories,
    content_type varchar(50),
    file_path    text,
    visible_name text
);

alter table upload
    owner to postgres;

create table s_language
(
    language_code varchar(10)  not null
        constraint pk_s_language
            primary key,
    language      varchar(255) not null,
    rtl           boolean default false,
    row_num       numeric(20)
);

alter table s_language
    owner to postgres;

create table s_language_keys
(
    id            numeric      not null
        constraint pk_s_language_keys
            primary key,
    language_code varchar(10)  not null
        constraint fk_langkey_lang
            references s_language,
    key_code      varchar(255) not null,
    parent_code   numeric
        constraint fk_langkey_langkey
            references s_language_keys,
    default_text  varchar(1000)
);

alter table s_language_keys
    owner to postgres;

create sequence seq_confirmation_token;
alter sequence seq_confirmation_token owner to postgres;

create sequence seq_password_reset_token;
alter sequence seq_password_reset_token owner to postgres;

create sequence seq_s_role;
alter sequence seq_s_role owner to postgres;

create sequence seq_email_templates;
alter sequence seq_email_templates owner to postgres;

create sequence seq_s_user;
alter sequence seq_s_user owner to postgres;

create sequence seq_s_user_profile;
alter sequence seq_s_user_profile owner to postgres;

create sequence seq_parameters;
alter sequence seq_parameters owner to postgres;

create sequence seq_directories;
alter sequence seq_directories owner to postgres;

create sequence seq_upload;
alter sequence seq_upload owner to postgres;

create sequence s_language_keys_id_seq;
alter sequence s_language_keys_id_seq owner to postgres;

create sequence seq_s_permission;
alter sequence seq_s_permission owner to postgres;

create sequence seq_s_menu;
alter sequence seq_s_menu owner to postgres;
