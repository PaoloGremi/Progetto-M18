CREATE USER 'tradecenter'@'localhost' IDENTIFIED BY 'Password1!';
GRANT USAGE ON *.* TO 'tradecenter'@'localhost';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON CARDS.* TO 'tradecenter'@'localhost';
GRANT CREATE VIEW ON CARDS.* TO 'tradecenter'@'localhost';