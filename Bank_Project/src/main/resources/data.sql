-- initial customer data
INSERT INTO Customer(id, name, surname) VALUES(1, 'Michael', 'Schumacher');
INSERT INTO Customer(id, name, surname) VALUES(2, 'Paul', 'Taylor');
INSERT INTO Customer(id, name, surname) VALUES(3, 'Michael', 'Jordan');
INSERT INTO Customer(id, name, surname) VALUES(4, 'Jenson', 'Button');
INSERT INTO Customer(id, name, surname) VALUES(5, 'Michael', 'Fassbender');

-- initial account data
INSERT INTO Account(id, account_type, owner_customer_id, balance) VALUES(1, 1, 1, 300);

INSERT INTO Account(id, account_type, owner_customer_id, balance) VALUES(2, 0, 2, 700);

INSERT INTO Account(id, account_type, owner_customer_id, balance) VALUES(3, 0, 3, 20000);

INSERT INTO Account(id, account_type, owner_customer_id, balance) VALUES(4, 0, 4, 380);
INSERT INTO Account(id, account_type, owner_customer_id, balance) VALUES(5, 1, 4, 140);

INSERT INTO Account(id, account_type, owner_customer_id, balance) VALUES(6, 1, 5, 140);

-- initial transaction data
INSERT INTO Transaction(id, other_party_id, amount, date, account_id) VALUES(1, 1, 10, {ts '2012-09-17 18:47:52.69'}, 1);
INSERT INTO Transaction(id, other_party_id, amount, date, account_id) VALUES(2, 2, 600, {ts '2013-02-23 10:55:34.07'}, 2);
INSERT INTO Transaction(id, other_party_id, amount, date, account_id) VALUES(3, 3, 800, {ts '2017-02-04 08:13:52.45'}, 3);
INSERT INTO Transaction(id, other_party_id, amount, date, account_id) VALUES(5, 3, 700, {ts '2017-02-04 08:13:52.45'}, 3);
INSERT INTO Transaction(id, other_party_id, amount, date, account_id) VALUES(4, 3, 900, {ts '2017-04-17 17:11:22.12'}, 4);