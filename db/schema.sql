DROP DATABASE IF EXISTS `todo_list`;
CREATE DATABASE IF NOT EXISTS `todo_list`;
USE `todo_list`;

-- Dumping structure for table todo_list.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT 'username must be unique',
  `email` varchar(255) NOT NULL COMMENT 'email must be unique',
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
);

-- Dumping structure for table todolist.priorities
DROP TABLE IF EXISTS `priorities`;
CREATE TABLE IF NOT EXISTS `priorities` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) COMMENT='The priorities of the tasks. If any values in here are changed, the code in the program must be changed also.';

-- Dumping data for table todo_list.priorities: ~5 rows (approximately)
INSERT INTO `priorities` (`id`, `name`) VALUES
	(1, 'PRIORITY_1'),
	(2, 'PRIORITY_2'),
	(3, 'PRIORITY_3'),
	(4, 'PRIORITY_4'),
	(5, 'PRIORITY_5');
	
-- Dumping structure for table todo_list.statuses
DROP TABLE IF EXISTS `statuses`;
CREATE TABLE IF NOT EXISTS `statuses` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);

-- Dumping data for table todo_list.statuses: ~4 rows (approximately)
INSERT INTO `statuses` (`id`, `name`) VALUES
	(1, 'STATUS_1'),
	(2, 'STATUS_2'),
	(3, 'STATUS_3'),
	(4, 'STATUS_4');

-- Dumping structure for table todo_list.projects
DROP TABLE IF EXISTS `projects`;
CREATE TABLE IF NOT EXISTS `projects` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `FK_projects_users` (`uid`),
  CONSTRAINT `FK_projects_users` FOREIGN KEY (`uid`) REFERENCES `users` (`id`)
);

-- Dumping structure for table todo_list.tasks
DROP TABLE IF EXISTS `tasks`;
CREATE TABLE IF NOT EXISTS `tasks` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `pid` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `duedate` date DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `FK_tasks_projects` (`pid`),
  KEY `FK_tasks_priority` (`priority`),
  KEY `FK_tasks_statuses` (`status`),
  CONSTRAINT `FK_tasks_priority` FOREIGN KEY (`priority`) REFERENCES `priorities` (`id`),
  CONSTRAINT `FK_tasks_projects` FOREIGN KEY (`pid`) REFERENCES `projects` (`id`),
  CONSTRAINT `FK_tasks_statuses` FOREIGN KEY (`status`) REFERENCES `statuses` (`id`)
);