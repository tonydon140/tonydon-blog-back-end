create database tonydon_blog;
use tonydon_blog;

# 文章表
create table td_article
(
    id           bigint auto_increment primary key,
    title        varchar(256)  default null comment '标题',
    content      longtext comment '文章内容',
    summary      varchar(1024) default null comment '文章摘要',
    category_id  bigint        default null comment '所属分类id',
    thumbnail    varchar(256)  default null comment '缩略图',
    view_count   bigint        default 0 comment '访问量',
    is_top       character     default 0 comment '是否置顶（0否，1是）',
    is_publish   character     default 0 comment '是否发布（0草稿，1已发布）',
    is_comment   character     default 1 comment '是否允许评论（0禁止，1允许）',

    publish_by   bigint        default null comment '发布人id',
    publish_time datetime      default now() comment '发布时间',
    update_by    bigint        default null comment '更新人id',
    update_time  datetime      default now() on update now() comment '更新时间',
    deleted      int           default 0 comment '删除标志（0代表未删除，1代表已删除）'
) default charset = utf8mb4 comment ='文章表';


# 创建分类表
create table td_category
(
    id          bigint primary key auto_increment,
    name        varchar(64) unique comment '分类名',
    description varchar(256) default null comment '描述',
    pid         bigint       default -1 comment '父分类id，如果没有父分类为 -1',
    status      character    default 0 comment '状态0:正常,1禁用',

    create_by   bigint       default null,
    create_time datetime     default now(),
    update_by   bigint       default null,
    update_time datetime     default now() on update now(),
    deleted     character    default 0 comment '删除标志（0代表未删除，1代表已删除）'
) default charset utf8mb4 comment '分类表';
insert into td_category(`id`, `name`, `pid`, `description`)
values (1, '未分类', -1, '未分类');


# 友链表
create table td_friend_link
(
    id          bigint not null auto_increment primary key,
    name        varchar(128) default null,
    logo        varchar(256) default null,
    description varchar(512) default null,
    address     varchar(256) default null comment '网站地址',
    status      character     default 2 comment '审核状态（0：审核未通过，1：审核通过，2：未审核）',

    create_time datetime     default now() comment '创建时间，自动设置',
    check_time  datetime     default null comment '审核时间',
    deleted     character    default 0 comment '删除标志（0代表未删除，1代表已删除）'
) default charset = utf8mb4 comment ='友链表';
drop table td_friend_link;

create table sys_user
(
    id           bigint auto_increment primary key comment '主键',
    username     varchar(64) not null unique comment '用户名',
    nickname     varchar(64) not null comment '昵称',
    password     varchar(64) not null comment '密码',
    type         character    default 0 comment '用户类型：0代表普通用户，1代表管理员',
    status       character    default 0 comment '账号状态（0正常 1停用）',
    email        varchar(64)  default null comment '邮箱',
    phone_number varchar(32)  default null comment '手机号',
    sex          character    default null comment '用户性别（0男，1女，2未知）',
    avatar       varchar(128) default null comment '头像',

    create_by    bigint       default null comment '创建人id',
    create_time  datetime     default now() comment '创建时间，自动设置',
    update_by    bigint       default null comment '更新人id',
    update_time  datetime     default now() on update now() comment '更新时间，自动设置',
    deleted      character    default 0 comment '删除标志（0代表未删除，1代表已删除）'
) default charset utf8mb4 comment '用户表';

# admin 1234
insert into sys_user(username, nickname, password, type, status, email)
values ('admin', '管理员', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', 1, 0, 'example@email.com');

# 评论表，无登陆匿名用户评论
create table td_comment
(
    id          bigint       not null auto_increment primary key,
    nickname    varchar(32)  not null comment '昵称',
    avatar      varchar(32)  not null comment '头像路径',
    email       varchar(32) default null comment '邮箱地址',
    article_id  bigint       not null comment '评论文章id',
    reply_id    bigint      default -1 comment '回复评论的id，-1表示没有回复',
    content     varchar(512) not null comment '评论内容',

    create_time datetime    default now() comment '创建时间',
    deleted     character   default 0 comment '删除标志（0代表未删除，1代表已删除）',
    foreign key fk (article_id) references td_article (id)
) default charset = utf8mb4 comment ='评论表';

# drop table td_comment, td_article, td_category, td_friend_link, sys_user;