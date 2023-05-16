create table SchoolChose
(
       sId     int not null,
       proId1       int           not null comment '专业号',
       proId2       int           null,
       proId3       int           null,
       proId4       int           null,
       index int not null comment '志愿顺序',
       acceptAdjust int default 1 not null comment '接受调剂'

)
    comment '志愿填报';

