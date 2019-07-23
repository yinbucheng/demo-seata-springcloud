CREATE TABLE `BUSINESS_ACCOUNT` (
`accountId` varchar(32) NOT NULL COMMENT '主键uuid',
`amount` int DEFAULT NULL COMMENT '金额(单位为分)',
`accountName` varchar(32) DEFAULT NULL COMMENT '账户名称',
`logicDel` char(1) DEFAULT NULL COMMENT '逻辑删除 Y:删除 N:未删除',
`remark` varchar(240) DEFAULT NULL COMMENT '备注',
PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `BUSINESS_ORDER` (
  `orderId` varchar(32) NOT NULL COMMENT '主键uuid',
  `orderNo` varchar(32) DEFAULT NULL COMMENT '订单号',
  `orderDetail` varchar(240) DEFAULT NULL COMMENT '订单详情',
  `price` int default NOT NULL comment '订单金额',
  `createTime` varchar(24) DEFAULT NULL COMMENT '创建时间',
  `logicDel` char(1) DEFAULT NULL COMMENT '逻辑删除 Y:删除 N:未删除',
  `remark` varchar(240) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `orderNo` (`orderNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `BUSINESS_STORAGE` (
  `storageId` varchar(32) NOT NULL COMMENT '主键uuid',
  `storageName` varchar(32) DEFAULT NULL COMMENT '仓储名称',
  `storageCount` int(11) DEFAULT NULL COMMENT '数量',
  `logicDel` char(1) DEFAULT NULL COMMENT '逻辑删除 Y:删除 N:未删除',
  `remark` varchar(240) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`storageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#这个表是seata在at模式下需要的表
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_unionkey` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8;