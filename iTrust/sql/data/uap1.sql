INSERT INTO Personnel
(MID,
AMID,
lastName,
firstName,
address1,
address2,
city,
state,
zip1,
zip2,
phone1,
phone2,
phone3
)
VALUES (
8000000009,
9000000000,
'LastUAP',
'FirstUAP',
'100 Ave',
'',
'Raleigh',
'NC',
'27607',
'',
'111',
'111',
'1111'
);

INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 
VALUES (8000000009, '1a3446ba73f27d62ce28d6fca6d90df17c271ef0', 'uap', 'opposite of yin', 'yang');
--password: uappass1