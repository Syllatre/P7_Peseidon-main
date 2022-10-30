TRUNCATE TABLE `user`;


INSERT INTO `user`(id, fullname, username, password, role) values
(1,'Administrator', 'admin', '$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u', 'ADMIN'),
(2,'User', 'user', '$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u', 'USER');