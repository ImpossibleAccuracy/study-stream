insert into `placement` (`city`, `address`, `latitude`, `longitude`, `timezone`) values
	("Moscow", "Leninva 102", 0, 0, "America/Sao_Paulo"),
	("St. Peterburg", "Leninva 10", 0, 0, "Asia/Kolkata"),
	("Sochi", "Leninva 32", 0, 0, "Europe/Moscow");
	
insert into `account` (`username`, `password`) values
	("Gamel", "12345678"),
	("Admin", "qazwsxedc"),
	("User2164", "81726354"),
	("Jinus", "87654321"),
	("Ornal", "qwerty"),
	("Magito", "wasdwasd");
	
insert into `device` (`account_id`, `name`, `token`, `type_id`) values
	(1, 'Samsung A20', "jkdfgdfgouireoirgeorignb", 1),
	(1, 'Windows 10', "sdjkv34v89ndvklb34", 3),
	(2, 'Iphone 15 PRO MAX', "f4bhfdgfdgfd90fdg", 2),
	(3, 'Google Chrome', "hgjkhkjhkjiuiu", 4);
	

INSERT INTO `event` (`creator_id`, `placement_id`, `teacher_id`, `title`, `description`, `thumb_id`, `background_id`, `max_persons`, `min_persons`) values
	(2, 1, 4, 'Huonai', 'Lorem ipsum...', null, null, 10, 2),
	(6, 2, 4, 'Yolonku', 'Lorem ipsum1...', null, null, 15, 1),
	(6, 3, 4, 'Jitonai', 'Lorem ipsum2...', null, null, 12, 2),
	(6, 2, 4, 'Lominutkey', 'Lorem ipsum3...', null, null, 13, 1),
	(6, 1, 4, 'Hinkai', 'Lorem ipsum4...', null, null, 20, 2),
	(6, 3, 4, 'Bankai', 'Lorem ipsum5...', null, null, 15, 1),
	(6, 1, 4, 'Mushitemitsu', 'Lorem ipsum6...', null, null, 10, 2);


INSERT INTO `schedule` (`creator_id`, `event_id`, `day_of_week`, `start_time`, `end_time`, `status_id`) values
	(2, 1, 0, '08:00', '10:00', 3),
	(2, 1, 2, '08:00', '10:00', 3),
	(2, 1, 3, '18:00', '20:00', 3),
	(2, 1, 6, '08:00', '10:00', 3),
	(6, 2, 1, '08:00', '10:00', 3),
	(6, 2, 1, '19:00', '21:00', 3),
	(6, 2, 4, '08:00', '10:00', 3),
	(6, 3, 1, '08:00', '10:00', 3),
	(6, 3, 2, '08:00', '10:00', 3),
	(6, 3, 3, '08:00', '10:00', 3),
	(6, 4, 0, '08:00', '10:00', 3),
	(6, 4, 2, '08:00', '10:00', 3),
	(6, 5, 1, '08:00', '10:00', 3),
	(6, 5, 1, '10:30', '12:30', 3),
	(6, 6, 5, '08:30', '15:00', 3),
	(6, 7, 6, '08:00', '10:00', 3);
