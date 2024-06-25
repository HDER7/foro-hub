create table courses(
    id bigint not null auto_increment,
    name varchar(100) not null unique ,
    category varchar(100) not null  ,
    primary key (id)
);