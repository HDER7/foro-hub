create table answers(
    id bigint not null auto_increment,
    message varchar(500) not null,
    creation datetime not null,
    topic_id bigint not null,
    author_id bigint not null,
    primary key (id),
    constraint fk_answer_author_id foreign key(author_id) references users(id),
    constraint fk_answer_topic_id foreign key(topic_id) references topics(id)
);