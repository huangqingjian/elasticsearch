CREATE TABLE `es_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名称',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `face` varchar(64) NOT NULL DEFAULT '' COMMENT '头像',
  `managed` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否管理员，0-否 1-是',
  `create_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否删除，0-正常 1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
CREATE TABLE `es_index` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '索引名称',
  `description` varchar(512) NOT NULL DEFAULT '' COMMENT '索引描述',
  `shards` int(11) NOT NULL DEFAULT '0' COMMENT '分片数',
  `replicas` int(11) NOT NULL DEFAULT '0' COMMENT '副本数',
  `create_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否删除，0-正常 1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='索引表';
CREATE TABLE `es_index_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '索引类型名称',
  `type` varchar(32) NOT NULL DEFAULT '' COMMENT '映射字段类型',
  `index_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '索引Id',
  `indexed` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否索引',
  `analyzer` varchar(32) NOT NULL DEFAULT '' COMMENT '索引分词',
  `search_analyzer` varchar(32) NOT NULL DEFAULT '' COMMENT '搜索分词',
  `create_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否删除，0-正常 1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='索引属性表';
CREATE TABLE `es_index_sql` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `index_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '索引id',
  `job_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'jobid',
  `sql` varchar(1024) NOT NULL COMMENT '查询SQL',
  `deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否删除，0-正常 1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='索引sql表';
CREATE TABLE `es_index_interface` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `index_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '索引id',
  `job_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'jobid',
  `url` varchar(1024) NOT NULL COMMENT 'url',
  `deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否删除，0-正常 1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='索引接口表';
CREATE TABLE `es_index_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `index_id` int(11) NOT NULL DEFAULT '0' COMMENT '索引Id',
  `index_name` varchar(128) NOT NULL DEFAULT '' COMMENT '索引名称',
  `switchs` tinyint(3) NOT NULL DEFAULT '0' COMMENT '开关:0 关，1：开',
  `data_sync_type` tinyint(3) NOT NULL COMMENT '数据同步方式：0：SQL，1：提供服务接口',
  `data_sync_all_rule` tinyint(3) NOT NULL DEFAULT '0' COMMENT '全量同步数据规则：1：每天一次，2：每周一次：3：每月一次',
  `data_sync_incr_rule` tinyint(3) NOT NULL DEFAULT '0' COMMENT '增量同步数据规则：1：5分钟一次，2：30分钟一次 3：60分钟一次',
  `last_sync_time` datetime DEFAULT NULL COMMENT '最近一次同步完成时间',
  `deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否删除，0-正常 1-删除',
  `create_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='索引job表';
CREATE TABLE `es_index_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `index_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '索引id',
  `index_name` varchar(128) NOT NULL DEFAULT '' COMMENT '索引名称',
  `job_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '任务id',
  `data_sync_type` tinyint(3) NOT NULL COMMENT '数据同步方式：0：SQL，1：提供服务接口',
  `start_time` datetime DEFAULT NULL COMMENT 'job开始时间',
  `end_time` datetime DEFAULT NULL COMMENT 'job结束时间',
  `total` int(11) DEFAULT NULL COMMENT '本次同步数据量',
  `error_code` varchar(3) NOT NULL DEFAULT '0' COMMENT '错误码：0-成功，1-失败',
  `error_message` varchar(1024) NOT NULL DEFAULT '' COMMENT '错误信息',
  `deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否删除，0-正常 1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='同步数据job记录表';
INSERT INTO es_user (id, user_name, password, face, managed, create_by, create_time, update_by, update_time, deleted) VALUES (1, 'admin', '96e79218965eb72c92a549dd5a330112', '', 1, 0, '2019-12-26 10:53:39', 0, '2020-02-04 15:32:04', 0);