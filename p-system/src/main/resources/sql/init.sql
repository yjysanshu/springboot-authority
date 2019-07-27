CREATE TABLE `configure` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '配置项名',
  `value` text NOT NULL COMMENT '配置项值',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '配置项中文描述',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统及参数配置项表';

CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `number` varchar(64) NOT NULL DEFAULT '' COMMENT '学生学号',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '学生姓名',
  `sex` enum('male', 'female') NOT NULL DEFAULT 'male' COMMENT '学生性别',
  `age` tinyint(4) NOT NULL DEFAULT '0' COMMENT '资源类型 0-菜单 1-功能',
  `grade` varchar(32) NOT NULL DEFAULT '' COMMENT '所属班级',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_number` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生信息表';
