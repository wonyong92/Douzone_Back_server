drop table if exists postvote;
drop table if exists comment;
drop table if exists post;
drop table if exists member;

create table member
(
    member_id varchar(30) not null
        primary key,
    name      varchar(30) null,
    pwd       varchar(30) null,
    email     varchar(40) null
);

create table post
(
    post_id   int auto_increment
        primary key,
    title     varchar(254)  null,
    content   longtext      null,
    writer_id varchar(30)   null,
    parent_id int           null,
    file1     varchar(254)  null,
    file2     varchar(254)  null,
    score     int default 0 null,
    constraint post_id
        unique (post_id, file1, file2),
    constraint post_member_writer_id_fk
        foreign key (writer_id) references member (member_id)
            on delete cascade,
    constraint post_post_parent_id_fk
        foreign key (parent_id) references post (post_id)
            on delete cascade
);

create table comment
(
    comment_id bigint auto_increment
        primary key,
    content    longtext    null,
    writer_id  varchar(30) null,
    post_id    int         null,
    constraint comment_member_writer_id_fk
        foreign key (writer_id) references member (member_id)
            on delete cascade,
    constraint comment_post_post_id_fk
        foreign key (post_id) references post (post_id)
            on delete cascade
);

create fulltext index idx_fulltext_title
    on post (title);

create index idx_post_id_desc
    on post (post_id desc);

create table postvote
(
    MEMBER_ID varchar(30)   not null,
    POST_ID   int           not null,
    SCORE     int default 0 null,
    primary key (MEMBER_ID, POST_ID),
    constraint postvote_ibfk_1
        foreign key (MEMBER_ID) references member (member_id),
    constraint postvote_ibfk_2
        foreign key (POST_ID) references post (post_id)
);

create index POST_ID
    on postvote (POST_ID);

