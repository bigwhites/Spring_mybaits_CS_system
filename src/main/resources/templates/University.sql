create table University
(
    uId   int auto_increment comment '大学id',
    uName VARCHAR(32) not null comment '校名',
    constraint University_pk
        primary key (uId)
)
    comment '大学表' auto_increment = 200000;

