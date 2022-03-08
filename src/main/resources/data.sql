INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (1, 6, 10000, 12, 'consumer');
INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (2, 6, 2000, 1, 'consumer');
INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (3, 6, 1000, 3, 'consumer');
INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (4, 6, 1000, 2, 'consumer');
INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (5, 6, 10000, 1, 'consumer');
INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (6, 12, 8000, 3, 'student');
INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (7, 12, 90000, 6, 'student');
INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (8, 12, 10000, 4, 'student');
INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (9, 12, 6000, 6, 'student');
INSERT INTO loan (id, annual_interest, requested_amount, term_months, type) VALUES (10, 12, 5000, 4, 'student');

INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (1, 33, 3000,10000, 6, false, 'James Gosling');
INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (2, 35, 4000,20000, 6, true, 'Ricardo Bazan');
INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (3, 30, 3500,15000, 6, false, 'Percy Muldoon');
INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (4, 40, 5000,50000, 6, true, 'Sonya Barzel');
INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (5, 37, 3000,25000, 6, false, 'Jamie Bunrs');
INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (6, 25, 2000,9000, 6, false, 'Jorge Masvidal');
INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (7, 24, 2500,10000, 6, false, 'Juan Carlos');
INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (8, 21, 25000,10000, 6, false, 'Julio Lopez');
INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (9, 22, 1500,8000, 6, false, 'Esteban Princi');
INSERT INTO borrower (id, age, annual_debt, annual_income, credit_history, delinquent_debt, name) VALUES (10, 27, 2000,10000, 6, false, 'Matias Corte');

/*
select * from loan where loan.id = 1;
select * from borrower where borrower.id = 1;

select * from loan;
select * from borrower;
SELECT l.*, b.name, b.age, b.delinquent_debt
FROM loan AS l
JOIN borrower AS b
ON l.id = b.id WHERE b.age > 22
*/