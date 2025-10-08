create database minitodo default character set utf8 default collate utf8_general_ci;
use minitodo;

drop table Todo;
create table if not exists Todo(
    id bigint auto_increment not null primary key,
    user_id varchar(255) not null,
    title varchar(255) not null,
    done boolean default false
);
desc Todo;
