-- Insert Wallet Data
INSERT INTO wallet (id, currency, balance, created_at, updated_at, name)
VALUES (uuid_generate_v4(), 'EUR', 1000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Savings Wallet'),
       (uuid_generate_v4(), 'EUR', 2000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Travel Wallet'),
       (uuid_generate_v4(), 'CZK', 1500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Business Wallet'),
       (uuid_generate_v4(), 'EUR', 500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Emergency Wallet'),
       (uuid_generate_v4(), 'CZK', 2500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Investment Wallet'),
       (uuid_generate_v4(), 'CZK', 3000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Home Savings Wallet');


-- Insert Transaction Data
-- With account_number and bank_code for deposits and withdrawals, and without it for transfers

-- 1. Deposit Transaction (Depositing money into the "Savings Wallet" from Bank A)
INSERT INTO transaction (id, sender_wallet_id, receiver_wallet_id, account_number, bank_code, amount, currency, transaction_type, transaction_status, created_at, updated_at)
VALUES (
           uuid_generate_v4(),
           NULL,
           (SELECT id FROM wallet WHERE name = 'Savings Wallet'),
           '123456789012', '1100',
           500.00, 'EUR', 'DEPOSIT', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- 2. Withdrawal Transaction (Withdrawing money from "Travel Wallet" to Bank B)
INSERT INTO transaction (id, sender_wallet_id, receiver_wallet_id, account_number, bank_code, amount, currency, transaction_type, transaction_status, created_at, updated_at)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM wallet WHERE name = 'Travel Wallet'),
           NULL,
           '987654321098', '2200',
           1000.00, 'EUR', 'WITHDRAWAL', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- 3. Transfer Transaction (Transferring from "Business Wallet" to "Emergency Wallet")
INSERT INTO transaction (id, sender_wallet_id, receiver_wallet_id, account_number, bank_code, amount, currency, transaction_type, transaction_status, created_at, updated_at)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM wallet WHERE name = 'Business Wallet'),
           (SELECT id FROM wallet WHERE name = 'Emergency Wallet'),
           NULL, NULL,
           200.00, 'CZK', 'TRANSFER', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- 4. Transfer Transaction (Transferring from "Investment Wallet" to "Home Savings Wallet")
INSERT INTO transaction (id, sender_wallet_id, receiver_wallet_id, account_number, bank_code, amount, currency, transaction_type, transaction_status, created_at, updated_at)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM wallet WHERE name = 'Investment Wallet'),
           (SELECT id FROM wallet WHERE name = 'Home Savings Wallet'),
           NULL, NULL,
           1500.00, 'CZK', 'TRANSFER', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- 5. Deposit Transaction (Depositing money into the "Travel Wallet" from Bank C)
INSERT INTO transaction (id, sender_wallet_id, receiver_wallet_id, account_number, bank_code, amount, currency, transaction_type, transaction_status, created_at, updated_at)
VALUES (
           uuid_generate_v4(),
           NULL,
           (SELECT id FROM wallet WHERE name = 'Travel Wallet'),
           '654321098765', '3300',
           1000.00, 'EUR', 'DEPOSIT', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- 6. Withdrawal Transaction (Withdrawing money from "Business Wallet" to Bank D)
INSERT INTO transaction (id, sender_wallet_id, receiver_wallet_id, account_number, bank_code, amount, currency, transaction_type, transaction_status, created_at, updated_at)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM wallet WHERE name = 'Business Wallet'),
           NULL,
           '321654987654', '4400',
           350.00, 'CZK', 'WITHDRAWAL', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- 7. Transfer Transaction (Transferring from "Savings Wallet" to "Investment Wallet")
INSERT INTO transaction (id, sender_wallet_id, receiver_wallet_id, account_number, bank_code, amount, currency, transaction_type, transaction_status, created_at, updated_at)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM wallet WHERE name = 'Savings Wallet'),
           (SELECT id FROM wallet WHERE name = 'Investment Wallet'),
           NULL, NULL,
           1000.00, 'EUR', 'TRANSFER', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- 8. Deposit Transaction (Depositing money into "Emergency Wallet" from Bank E)
INSERT INTO transaction (id, sender_wallet_id, receiver_wallet_id, account_number, bank_code, amount, currency, transaction_type, transaction_status, created_at, updated_at)
VALUES (
           uuid_generate_v4(),
           NULL,
           (SELECT id FROM wallet WHERE name = 'Emergency Wallet'),
           '987123654321', '5500',
           800.00, 'EUR', 'DEPOSIT', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );