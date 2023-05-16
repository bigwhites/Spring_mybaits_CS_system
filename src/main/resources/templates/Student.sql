create table Student
(
    sId          INT auto_increment comment '高考考号',
    sName        VARCHAR(32) not null comment '学生姓名',
    sSex         CHAR(6) not null comment '性别',
    highSchoolId INT     not null comment '高中学校号',
    chineseScore int     not null comment '语文成绩',
    mathScore    int     not null comment '数学成绩',
    englishScore int     not null comment '英语成绩',
    typeFlag     int     not null comment '物理/历史标记',
    sub1Score    int     not null comment '物理/历史成绩',
    sub2Id       int     not null comment '选科2',
    sub2Score    int     not null comment '选科2成绩',
    sub3Id       int     not null comment '选科3',
    sub3Score    int     not null comment '选科3成绩',
    totalScore   int     not null comment '总分',
    passWordMd5  varchar(256) not null comment '登录密码密文',
    constraint Student_pk
        primary key (sId)
)
    comment '学生表' auto_increment = 210100;