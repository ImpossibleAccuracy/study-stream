insert into `role` (`title`) values ("User"), ("Admin"), ("Moderator")/*, ("Manager"), ("Teacher")*/;

insert into `privilege` (`title`) values
    ("READ_ALL_DEVICES"),
    ("READ_ACCOUNTS"),
    ("WRITE_ACCOUNTS"),
    ("DELETE_ACCOUNTS");


insert into `privilege_role` (`role_id`, `privilege_id`) values
    (2, 1), (2, 2), (2, 3), (2, 4),
    (3, 1), (3, 2), (3, 3);
