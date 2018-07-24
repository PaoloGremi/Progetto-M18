CREATE USER 'tradecenter_test'@'localhost' IDENTIFIED BY 'Password1!';
GRANT USAGE ON *.* TO 'tradecenter_test'@'localhost';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON test_cards.* TO 'tradecenter_test'@'localhost';
GRANT CREATE VIEW ON test_cards.* TO 'tradecenter_test'@'localhost';