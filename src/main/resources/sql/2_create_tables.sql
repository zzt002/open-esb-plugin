create table `test`.`esb_reload_fail`(
  id int primary key auto_increment,
  create_time timestamp not null default now(),
  reload_url varchar(1024) comment '重载请求地址',
  retry_count int default 0 comment '重试次数',
  error_message varchar(1024) comment '错误信息',
  unique (`reload_url`)
) engine=InnoDB default charset=utf8 comment='重载失败';

create table `test`.esb_plugin_sys_log(
	id int auto_increment primary key,
	create_date datetime ,
	modify_date datetime ,
	status tinyint(2) ,
	title varchar(255) ,
	action varchar(255) ,
	ip varchar(255) ,
	url varchar(255) ,
	operation_status tinyint(2) ,
	param varchar(2048) ,
	message varchar(1024)
)engine=InnoDB default charset utf8 comment='系统请求日志';

create table `test`.esb_plugin_task_config(
	id int not null primary key,
	name varchar(255) ,
	description varchar(255) ,
	open varchar(5) ,
	`limit` varchar(50),
	exec_time timestamp default now() comment '执行时间'
)engine=InnoDB default charset utf8 comment='定时任务配置';

create table `test`.ip_white(
	ip varchar(255) ,
	constraint index_ip
  unique (ip)
)engine=InnoDB default charset utf8 comment='ip白名单';

create table `test`.`esb_user`(
	id int auto_increment primary key,
	create_date datetime,
	modify_date datetime,
	account varchar(255) not null,
	password varchar(255) not null,
	name varchar(255),
	status tinyint,
	unique (account)
)engine=InnoDB default charset utf8 comment='用户';

create table `test`.`menu`(
  id int auto_increment primary key,
  title varchar(20) not null comment '菜单名',
  `type` tinyint not null default 1 comment '类型： 1菜单展示 2功能请求',
  p_id int not null default 0 comment '父级id',
  url varchar(50) comment '接口请求url地址',
  sort int default 99 comment '排序'
)engine=InnoDB default charset utf8 comment='菜单';

create table `test`.`role`(
  id int auto_increment primary key,
  `name` varchar(20) comment '角色名'
)engine=InnoDB default charset utf8 comment='角色';

create table `test`.`role_menu_relationship`(
  role_id int not null,
  menu_id int not null
)engine=InnoDB charset utf8 comment='菜单-角色关系';

create table `test`.`user_role_relationship`(
  role_id int not null,
  user_id int not null
)engine=InnoDB charset utf8 comment='用户-角色关系';