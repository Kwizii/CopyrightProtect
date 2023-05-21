create table copyright_protect.user
(
    id                     int auto_increment
        primary key,
    username               varchar(32)  null comment '用户名',
    password               varchar(64)  null comment '密码',
    private_key            varchar(64)  null comment '区块链用户私钥',
    pub_key                varchar(128) null comment '区块链用户公钥',
    user_chain_address     varchar(64)  null comment '区块链用户账号地址',
    contract_chain_address varchar(64)  null comment '版权库智能合约地址'
);


create table copyright_protect.copyright
(
    id                 int auto_increment
        primary key,
    user_id            int          null comment '用户id',
    tx_hash            char(66)     null comment '交易哈希',
    origin_file_url    varchar(256) null comment '原文件存储地址',
    watermark_file_url varchar(256) null comment '水印文件地址',
    title              text         null comment '图像标题',
    content            text         null comment '水印内容',
    create_time        datetime     null comment '创建时间'
);

create index copyright_user_id_index
    on copyright_protect.copyright (user_id);

