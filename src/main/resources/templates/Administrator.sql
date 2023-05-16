create table Administrator
(
    aName       VARCHAR(24)  not null comment '管理员主键',
    passWordMd5 VARCHAR(128) not null,
    constraint Administrator_pk
        primary key (aName)
)
    comment '管理员';

