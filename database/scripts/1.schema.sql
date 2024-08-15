CREATE TABLE `document_type` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `mime_type` varchar(255) NOT NULL
);

CREATE TABLE `document` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `hash` varchar(255) NOT NULL,
  `path` varchar(2048) NOT NULL,
  `type_id` int NOT NULL
);

CREATE TABLE `privilege` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL
);

CREATE TABLE `role` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL
);

CREATE TABLE `account` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
);

CREATE TABLE `profile` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `patronymic` varchar(255) NOT NULL,
  `birthday` date NOT NULL,
  `avatar_id` int
);

CREATE TABLE `role_account` (
  `role_id` int NOT NULL,
  `account_id` int NOT NULL,
  PRIMARY KEY (`role_id`, `account_id`)
);

CREATE TABLE `privilege_role` (
  `role_id` int NOT NULL,
  `privilege_id` int NOT NULL,
  PRIMARY KEY (`role_id`, `privilege_id`)
);

CREATE TABLE `device_type` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL
);

CREATE TABLE `device` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL,
  `name` varchar(255),
  `token` varchar(255) NOT NULL,
  `type_id` int NOT NULL
);

CREATE TABLE `placement` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `timezone` varchar(255) NOT NULL
);

CREATE TABLE `event` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `creator_id` int NOT NULL,
  `placement_id` int NOT NULL,
  `teacher_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `thumb_id` int,
  `background_id` int,
  `max_persons` int NOT NULL,
  `min_persons` int
);

CREATE TABLE `schedule_status` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL
);

CREATE TABLE `schedule` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `creator_id` int NOT NULL,
  `event_id` int NOT NULL,
  `day_of_week` int NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `status_id` int NOT NULL
);

CREATE TABLE `event_exception_type` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL
);

CREATE TABLE `event_exception` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `creator_id` int NOT NULL,
  `event_id` int NOT NULL,
  `type_id` int NOT NULL,
  `datetime` datetime NOT NULL
);

CREATE TABLE `ticket_type` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `creator_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `total_events` int,
  `duration_days` int
);

CREATE TABLE `ticket` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `creator_id` int NOT NULL,
  `profile_id` int NOT NULL,
  `type_id` int NOT NULL,
  `activated_at` datetime,
  `closed_at` datetime
);

CREATE TABLE `schedule_attendee_status` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL
);

CREATE TABLE `schedule_attendee` (
  `schedule_id` int NOT NULL,
  `profile_id` int NOT NULL,
  `creator_id` int NOT NULL,
  `ticket_id` int NOT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`schedule_id`, `profile_id`)
);

CREATE UNIQUE INDEX `title` ON `document_type` (`title`);

CREATE UNIQUE INDEX `hash` ON `document` (`hash`);

CREATE UNIQUE INDEX `title` ON `privilege` (`title`);

CREATE UNIQUE INDEX `title` ON `role` (`title`);

CREATE UNIQUE INDEX `username` ON `account` (`username`);

CREATE UNIQUE INDEX `title` ON `device_type` (`title`);

CREATE UNIQUE INDEX `title` ON `schedule_status` (`title`);

CREATE UNIQUE INDEX `title` ON `event_exception_type` (`title`);

CREATE UNIQUE INDEX `title` ON `schedule_attendee_status` (`title`);

ALTER TABLE `document` ADD FOREIGN KEY (`type_id`) REFERENCES `document_type` (`id`) ON DELETE RESTRICT;

ALTER TABLE `profile` ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE;

ALTER TABLE `profile` ADD FOREIGN KEY (`avatar_id`) REFERENCES `document` (`id`) ON DELETE RESTRICT;

ALTER TABLE `role_account` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE;

ALTER TABLE `role_account` ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE;

ALTER TABLE `privilege_role` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE;

ALTER TABLE `privilege_role` ADD FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`) ON DELETE CASCADE;

ALTER TABLE `device` ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE;

ALTER TABLE `device` ADD FOREIGN KEY (`type_id`) REFERENCES `device_type` (`id`) ON DELETE RESTRICT;

ALTER TABLE `event` ADD FOREIGN KEY (`thumb_id`) REFERENCES `document` (`id`) ON DELETE RESTRICT;

ALTER TABLE `event` ADD FOREIGN KEY (`background_id`) REFERENCES `document` (`id`) ON DELETE RESTRICT;

ALTER TABLE `schedule` ADD FOREIGN KEY (`creator_id`) REFERENCES `account` (`id`) ON DELETE RESTRICT;

ALTER TABLE `schedule` ADD FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE;

ALTER TABLE `schedule` ADD FOREIGN KEY (`status_id`) REFERENCES `schedule_status` (`id`) ON DELETE RESTRICT;

ALTER TABLE `event_exception` ADD FOREIGN KEY (`creator_id`) REFERENCES `account` (`id`) ON DELETE RESTRICT;

ALTER TABLE `event_exception` ADD FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE RESTRICT;

ALTER TABLE `event_exception` ADD FOREIGN KEY (`type_id`) REFERENCES `event_exception_type` (`id`) ON DELETE RESTRICT;

ALTER TABLE `ticket_type` ADD FOREIGN KEY (`creator_id`) REFERENCES `account` (`id`) ON DELETE RESTRICT;

ALTER TABLE `ticket` ADD FOREIGN KEY (`creator_id`) REFERENCES `account` (`id`) ON DELETE CASCADE;

ALTER TABLE `ticket` ADD FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON DELETE CASCADE;

ALTER TABLE `ticket` ADD FOREIGN KEY (`type_id`) REFERENCES `ticket_type` (`id`) ON DELETE RESTRICT;

ALTER TABLE `schedule_attendee` ADD FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`) ON DELETE RESTRICT;

ALTER TABLE `schedule_attendee` ADD FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON DELETE CASCADE;

ALTER TABLE `schedule_attendee` ADD FOREIGN KEY (`creator_id`) REFERENCES `account` (`id`) ON DELETE CASCADE;

ALTER TABLE `schedule_attendee` ADD FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`) ON DELETE RESTRICT;

ALTER TABLE `schedule_attendee` ADD FOREIGN KEY (`status_id`) REFERENCES `schedule_attendee_status` (`id`) ON DELETE RESTRICT;
