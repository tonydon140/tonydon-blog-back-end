create database tonydon_blog;
use tonydon_blog;

# 文章表
create table td_article
(
    id           bigint auto_increment primary key,
    title        varchar(256)  default null comment '标题',
    content      longtext COMMENT '文章内容',
    summary      varchar(1024) default null comment '文章摘要',
    category_id  bigint        default null comment '所属分类id',
    thumbnail    varchar(256)  default null comment '缩略图',
    view_count   bigint        default '0' comment '访问量',
    is_top       char(1)       default '0' comment '是否置顶（0否，1是）',
    is_publish   char(1)       default '0' comment '是否发布（0草稿，1已发布）',
    is_comment   char(1)       default '1' comment '是否允许评论（0禁止，1允许）',
    publish_by   bigint        default null comment '发布人id',
    publish_time datetime      default null comment '发布事件',
    update_by    bigint        default null comment '更新人时间',
    update_time  datetime      default null comment '更新时间',
    del_flag     int           default '0' comment '删除标志（0代表未删除，1代表已删除）'
) DEFAULT CHARSET = utf8mb4 COMMENT ='文章表';


# 创建分类表
create table td_category
(
    id            bigint primary key auto_increment,
    name          varchar(64) unique comment '分类名',
    description   varchar(256) default null comment '描述',
    pid           bigint       default -1 comment '父分类id，如果没有父分类为 -1',
    status        char(1)      default '0' comment '状态0:正常,1禁用',
    `create_by`   bigint       default null,
    `create_time` datetime     default null,
    `update_by`   bigint       default null,
    `update_time` datetime     default null,
    `del_flag`    int          default '0' comment '删除标志（0代表未删除，1代表已删除）'
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
    status      char(1)      default '2' comment '审核状态（0：审核未通过，1：审核通过，2：未审核）',
    create_time datetime     DEFAULT NULL comment '创建时间',
    check_time  datetime     default null comment '审核时间',
    del_flag    int          DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）'
) DEFAULT CHARSET = utf8mb4 COMMENT ='友链表';


# 创建用户表
CREATE TABLE `sys_user`
(
    `id`           bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`     varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
    `nickname`     varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
    `password`     varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
    `type`         char(1)              DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
    `status`       char(1)              DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
    `email`        varchar(64)          DEFAULT NULL COMMENT '邮箱',
    `phone_number` varchar(32)          DEFAULT NULL COMMENT '手机号',
    `sex`          char(1)              DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
    `avatar`       varchar(128)         DEFAULT NULL COMMENT '头像',
    `create_by`    bigint               DEFAULT NULL COMMENT '创建人的用户id',
    `create_time`  datetime             DEFAULT NULL COMMENT '创建时间',
    `update_by`    bigint               DEFAULT NULL COMMENT '更新人',
    `update_time`  datetime             DEFAULT NULL COMMENT '更新时间',
    `del_flag`     int                  DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

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
    create_time datetime    default null comment '创建时间',
    del_flag    int         default 0 comment '删除标志（0代表未删除，1代表已删除）',
    foreign key fk (article_id) references td_article (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='评论表';