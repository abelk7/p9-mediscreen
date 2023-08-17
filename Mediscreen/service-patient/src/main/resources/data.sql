INSERT INTO patients (id, address, dob,family, given, phone, sex)
values (null, '1 Brookside St', CAST('1966-12-31 ' AS DATE), 'TestNone', 'Test', '100-222-3333', 'F'),
       (null, '2 High St', CAST('1945-06-24' AS DATE), 'TestBorderline', 'Test', '200-333-4444', 'M'),
       (null, '3 Club Road', CAST('2004-06-18' AS DATE), 'TestInDanger', 'Test', '300-444-5555', 'M'),
       (null, '4 Valley Dr', CAST('2002-08-28' AS DATE), 'TestEarlyOnset',  'Test', '400555-6666', 'F');