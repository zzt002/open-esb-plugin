INSERT INTO `test`.`esb_plugin_task_config`(`id`, `name`, `description`, `open`, `limit`, `exec_time`) VALUES (1, '开工单', '工作日11,15,17点执行，开启最近30单，时间小于等于limit天内的单子', '1', '3', DATE_ADD(NOW(), INTERVAL -30 MINUTE));
INSERT INTO `test`.`esb_plugin_task_config`(`id`, `name`, `description`, `open`) VALUES (2, 'MYSQL备份', '每天凌晨备份', '1');

INSERT INTO `test`.`esb_user`(`id`, `create_date`, `modify_date`, `account`, `password`, `name`, `status`) VALUES (1, '2020-08-06 09:56:24', NULL, 'user', '7ed21143076d0cca420653d4345baa2f', '管理员', 1);
