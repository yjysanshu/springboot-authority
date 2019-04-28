-------
-- 后台权限管理表
-------

CREATE TABLE `admin_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_phone` char(11) NOT NULL COMMENT '手机号',
  `user_name` varchar(16) NOT NULL DEFAULT '' COMMENT '姓名',
  `user_email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `user_password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `user_token` varchar(64) NOT NULL DEFAULT '' COMMENT '登录的token',
  `user_avatar` varchar(128) NOT NULL DEFAULT '' COMMENT '用户头像',
  `user_login_count` int(11) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `user_last_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后登录ip',
  `user_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0-正常 1-停用',
  `user_create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uniq_user_phone` (`user_phone`),
  UNIQUE KEY `uniq_user_email` (`user_email`),
  KEY `idx_user_token` (`user_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理后台用户表';
INSERT INTO `admin_user` (`user_phone`, `user_name`, `user_email`, `user_password`, `user_token`)
VALUES ('13800000000', '超级管理员', 'admin@admin.com', '$2a$10$L.MpN/awa/rl5DTJJVBDAeAJ7h1sBa3ikwkTIPX/TkayqR7iPBF1i', 'af4a8f4a0a304668aa2cac6e8839edd4');

CREATE TABLE `admin_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父角色id',
  `role_name` varchar(16) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '角色描述',
  `role_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0-正常 1-停用',
  `role_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型 0-成员角色member 1-群组角色group',
  `role_create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `role_update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uniq_role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台角色表';

INSERT INTO `admin_role` VALUES (1,0,'超级管理员','拥有所有权限',0,1,'2018-06-10 14:31:45','2018-06-10 14:31:45','','');

CREATE TABLE `admin_role_user` (
  `role_user_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_user_role_id` int(11) NOT NULL COMMENT '角色id',
  `role_user_user_id` int(11) NOT NULL COMMENT '用户id',
  `role_user_create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `role_user_update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_user_id`),
  KEY `idx_role_user_role_id` (`role_user_role_id`),
  KEY `idx_role_user_user_id` (`role_user_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色用户关联表';

INSERT INTO `admin_role_user` VALUES (1,1,1,'2018-06-19 17:07:09','2018-06-19 17:07:09','','');

CREATE TABLE `admin_resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `resource_parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '资源父id',
  `resource_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '资源类型 0-菜单 1-功能',
  `resource_target` varchar(64) NOT NULL DEFAULT '' COMMENT '语义化索引',
  `resource_data` varchar(1024) NOT NULL DEFAULT '' COMMENT '资源data，json格式',
  `resource_create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `resource_update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`resource_id`),
  KEY `idx_resource_parent_id` (`resource_parent_id`),
  KEY `idx_resource_target` (`resource_target`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源(菜单/接口)表';

INSERT INTO `admin_resource`(resource_id, resource_parent_id, resource_data)
VALUES
    (1,0,'{"title":"系统管理","path":"/system","icon":"","seq":50,"type":"layout"}'),
    (2,1,'{"title":"菜单管理","path":"system/menu","icon":"","seq":9,"type":"default"}'),
    (3,1,'{"title":"用户管理","path":"system/user","icon":"","seq":8,"type":"default"}'),
    (4,1,'{"title":"角色管理","path":"system/role","icon":"","seq":7,"type":"default"}');

CREATE TABLE `admin_privilege` (
  `privilege_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `privilege_resource_id` int(11) NOT NULL DEFAULT '0' COMMENT '资源id',
  `privilege_role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `privilege_create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `privilege_update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`privilege_id`),
  KEY `idx_privilege_resource_id` (`privilege_resource_id`),
  KEY `idx_privilege_role_id` (`privilege_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

--添加用户的角色
DELIMITER ;;
CREATE PROCEDURE `addUserRole`(IN userId INT, in roleIds_source varchar(50))
BEGIN
	DECLARE s int DEFAULT 0;
	DECLARE roleId int;
	DECLARE roleId_add varchar(11) default '';
	DECLARE i int;
	DECLARE num int;
	DECLARE roleIds_data varchar(50) default '';
	DECLARE roleIds_del varchar(50) default '';
	DECLARE roleIds_add varchar(50) default '';
	DECLARE del_sql varchar(200) default '';

	DECLARE roleUser CURSOR FOR SELECT role_user_role_id FROM admin_role_user WHERE role_user_user_id=userId;
  	DECLARE CONTINUE HANDLER FOR NOT FOUND SET s=1;

  	OPEN roleUser;
  		FETCH roleUser INTO roleId;
  		WHILE s <> 1 DO
  			SET roleIds_data = CONCAT(roleIds_data, CONCAT(roleId, ','));
  			FETCH roleUser INTO roleId;
  		END WHILE;
  	CLOSE roleUser;
  	SET roleIds_data = CONCAT(',', roleIds_data);

  	SET roleIds_del = roleIds_data;
  	SET num = (length(roleIds_source) - length(replace(roleIds_source,',','')));
  	SET i = 1;
  	WHILE i <= num DO
  		SET roleIds_del = replace(roleIds_del,CONCAT(',', reverse(substring_index(reverse(substring_index(roleIds_source,',', i)),',',1)), ','), ',');
  		SET i = i + 1;
  	END WHILE;

  	SET num = (length(roleIds_del) - length(replace(roleIds_del,',','')));
  	SET roleIds_del = substring_index(roleIds_del,',', num);
  	SET roleIds_del = reverse(substring_index(reverse(roleIds_del), ',', num - 1));

  	SET roleIds_add = roleIds_source;
  	SET num = (length(roleIds_data) - length(replace(roleIds_data,',','')));
  	SET i = 1;
  	WHILE i <= num DO
  		SET roleIds_add = replace(roleIds_add,CONCAT(',', reverse(substring_index(reverse(substring_index(roleIds_data,',', i)),',',1)), ','), ',');
  		SET i = i + 1;
  	END WHILE;

  	SET num = (length(roleIds_add) - length(replace(roleIds_add,',','')));
  	SET roleIds_add = substring_index(roleIds_add,',', num);
  	SET roleIds_add = reverse(substring_index(reverse(roleIds_add), ',', num - 1));

  	if roleIds_del != '' then
  	  SET @del_sql = CONCAT("delete from admin_role_user where role_user_role_id in (", roleIds_del, ") and role_user_user_id=", userId);
  		prepare mainStmt from @del_sql;
  		execute mainStmt;
  		deallocate prepare mainStmt;
  	end if;

  	if roleIds_add != '' then
    	SET num = (length(roleIds_add) - length(replace(roleIds_add,',',''))) + 1;
  		SET i = 1;
  		WHILE i <= num DO
  			SET roleId_add = reverse(substring_index(reverse(substring_index(roleIds_add,',', i)),',',1));
  			insert into admin_role_user(role_user_role_id, role_user_user_id) values(roleId_add, userId);
  			SET i = i + 1;
  		END WHILE;
  	end if;
END;;
DELIMITER ;

--添加角色的用户
DELIMITER ;;
CREATE PROCEDURE `addRoleUser`(IN roleId INT, in userIds_source varchar(50))
BEGIN
	DECLARE s int DEFAULT 0;
	DECLARE userId int;
	DECLARE userId_add varchar(11) default '';
	DECLARE i int;
	DECLARE num int;
	DECLARE userIds_data varchar(50) default '';
	DECLARE userIds_del varchar(50) default '';
	DECLARE userIds_add varchar(50) default '';
	DECLARE del_sql varchar(200) default '';

	DECLARE userRole CURSOR FOR SELECT role_user_user_id FROM admin_role_user WHERE role_user_role_id=roleId;
  	DECLARE CONTINUE HANDLER FOR NOT FOUND SET s=1;

  	OPEN userRole;
  		FETCH userRole INTO userId;
  		WHILE s <> 1 DO
  			SET userIds_data = CONCAT(userIds_data, CONCAT(userId, ','));
  			FETCH userRole INTO userId;
  		END WHILE;
  	CLOSE userRole;
  	SET userIds_data = CONCAT(',', userIds_data);

  	SET userIds_del = userIds_data;
  	SET num = (length(userIds_source) - length(replace(userIds_source,',','')));
  	SET i = 1;
  	WHILE i <= num DO
  		SET userIds_del = replace(userIds_del,CONCAT(',', reverse(substring_index(reverse(substring_index(userIds_source,',', i)),',',1)), ','), ',');
  		SET i = i + 1;
  	END WHILE;

  	SET num = (length(userIds_del) - length(replace(userIds_del,',','')));
  	SET userIds_del = substring_index(userIds_del,',', num);
  	SET userIds_del = reverse(substring_index(reverse(userIds_del), ',', num - 1));

  	SET userIds_add = userIds_source;
  	SET num = (length(userIds_data) - length(replace(userIds_data,',','')));
  	SET i = 1;
  	WHILE i <= num DO
  		SET userIds_add = replace(userIds_add,CONCAT(',', reverse(substring_index(reverse(substring_index(userIds_data,',', i)),',',1)), ','), ',');
  		SET i = i + 1;
  	END WHILE;

  	SET num = (length(userIds_add) - length(replace(userIds_add,',','')));
  	SET userIds_add = substring_index(userIds_add,',', num);
  	SET userIds_add = reverse(substring_index(reverse(userIds_add), ',', num - 1));

  	if userIds_del != '' then
  		SET @del_sql = CONCAT("delete from admin_role_user where role_user_user_id in (", userIds_del, ") and role_user_role_id=", roleId);
  		prepare mainStmt from @del_sql;
  		execute mainStmt;
  		deallocate prepare mainStmt;
  	end if;

  	if userIds_add != '' then
    	SET num = (length(userIds_add) - length(replace(userIds_add,',',''))) + 1;
  		SET i = 1;
  		WHILE i <= num DO
  			SET userId_add = reverse(substring_index(reverse(substring_index(userIds_add,',', i)),',',1));
  			insert into admin_role_user(role_user_role_id, role_user_user_id) values(roleId, userId_add);
  			SET i = i + 1;
  		END WHILE;
  	end if;
END;;
DELIMITER ;