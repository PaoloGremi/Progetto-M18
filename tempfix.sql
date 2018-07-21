ALTER TABLE `CARDS`.`cards_active` 
DROP PRIMARY KEY,
ADD PRIMARY KEY (`trade_id`, `card_id`);