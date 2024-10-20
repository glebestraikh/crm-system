INSERT INTO seller (name, contact_info, registration_date)
VALUES
    ('John Doe', 'john.doe@example.com', '2023-01-01 10:00:00'),
    ('Jane Smith', 'jane.smith@example.com', '2023-02-01 11:00:00'),
    ('Alice Johnson', 'alice.johnson@example.com', '2023-03-01 12:00:00'),
    ('Bob Brown', 'bob.brown@example.com', '2023-04-01 09:00:00'),
    ('Charlie Davis', 'charlie.davis@example.com', '2023-05-01 13:00:00'),
    ('Eve Black', 'eve.black@example.com', '2023-06-01 14:00:00'),
    ('Frank White', 'frank.white@example.com', '2023-07-01 08:00:00');



INSERT INTO transaction (seller_id, amount, payment_type, transaction_date)
VALUES
    -- Transactions for John Doe (seller_id = 1)
    (1, 100, 'CASH', '2023-01-02 10:15:00'),
    (1, 200, 'CARD', '2023-01-03 11:30:00'),
    (1, 150, 'TRANSFER', '2023-01-05 12:00:00'),
    (1, 250, 'CARD', '2023-01-07 15:45:00'),

    -- Transactions for Jane Smith (seller_id = 2)
    (2, 150, 'TRANSFER', '2023-02-02 12:45:00'),
    (2, 300, 'CARD', '2023-02-03 13:00:00'),
    (2, 400, 'CASH', '2023-02-06 14:30:00'),

    -- Transactions for Alice Johnson (seller_id = 3)
    (3, 250, 'TRANSFER', '2023-03-02 14:15:00'),
    (3, 400, 'CARD', '2023-03-03 15:30:00'),
    (3, 350, 'CASH', '2023-03-05 16:00:00'),

    -- Transactions for Bob Brown (seller_id = 4)
    (4, 500, 'CARD', '2023-04-02 09:30:00'),
    (4, 600, 'TRANSFER', '2023-04-03 10:00:00'),
    (4, 550, 'CASH', '2023-04-05 11:15:00'),

    -- Transactions for Charlie Davis (seller_id = 5)
    (5, 700, 'CASH', '2023-05-02 10:45:00'),
    (5, 800, 'CARD', '2023-05-03 12:30:00'),
    (5, 650, 'TRANSFER', '2023-05-06 13:00:00'),

    -- Transactions for Eve Black (seller_id = 6)
    (6, 900, 'CARD', '2023-06-02 09:45:00'),
    (6, 1000, 'TRANSFER', '2023-06-03 11:00:00'),
    (6, 850, 'CASH', '2023-06-05 12:15:00'),

    -- Transactions for Frank White (seller_id = 7)
    (7, 1200, 'CARD', '2023-07-02 10:00:00'),
    (7, 1300, 'TRANSFER', '2023-07-03 11:30:00'),
    (7, 1100, 'CASH', '2023-07-05 12:45:00');
