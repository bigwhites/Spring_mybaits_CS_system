create table Professional
(
    proId         int auto_increment comment '专业号',
    schId         int           not null comment '学校id',
    proName       VARCHAR(32)   not null comment '专业名称',
    forecastScore int           not null comment '预测分数线',
    maxCnt        int           not null comment '最大录取人数',
    curCnt        int default 0 not null comment '当前人数',
    minScore      int           not null comment '录取分数',
    constraint Professional_pk
        primary key (proId)
)
    comment '专业' auto_increment = 1000;

create index Professional_proName_index
    on Professional (proName);