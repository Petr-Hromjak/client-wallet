-- Insert User Data
INSERT INTO "user" (id, name, surname, email, password) VALUES
                                                            (uuid_generate_v4(), 'Alice', 'Johnson', 'alice.johnson@example.com', 'password123'),
                                                            (uuid_generate_v4(), 'Bob', 'Smith', 'bob.smith@example.com', 'password456'),
                                                            (uuid_generate_v4(), 'Charlie', 'Brown', 'charlie.brown@example.com', 'password789'),
                                                            (uuid_generate_v4(), 'Diana', 'Prince', 'diana.prince@example.com', 'password101'),
                                                            (uuid_generate_v4(), 'Eve', 'Adams', 'eve.adams@example.com', 'password202');

-- Insert Wallet Data (Associating users with wallets)
INSERT INTO wallet (id, user_id, currency, balance) VALUES
                                                        (uuid_generate_v4(), (SELECT id FROM "user" WHERE email = 'alice.johnson@example.com'), 'EUR', 1250.75),
                                                        (uuid_generate_v4(), (SELECT id FROM "user" WHERE email = 'alice.johnson@example.com'), 'CZK', 1250.75),
                                                        (uuid_generate_v4(), (SELECT id FROM "user" WHERE email = 'bob.smith@example.com'), 'CZK', 35600.50),
                                                        (uuid_generate_v4(), (SELECT id FROM "user" WHERE email = 'bob.smith@example.com'), 'EUR', 3600.50),
                                                        (uuid_generate_v4(), (SELECT id FROM "user" WHERE email = 'charlie.brown@example.com'), 'CZK', 980.00),
                                                        (uuid_generate_v4(), (SELECT id FROM "user" WHERE email = 'diana.prince@example.com'), 'EUR', 520.00);

-- Insert Transaction Data
-- With IBAN for deposits and withdrawals, and without IBAN for transfers

-- 1. Deposit Transaction (Alice deposits money from Bank A to her EUR wallet)
INSERT INTO transaction (id, sender_wallet_id, recipient_wallet_id, iban, amount, transaction_type, transaction_status, created_at, updated_at) VALUES
    (uuid_generate_v4(), NULL, (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'alice.johnson@example.com') AND currency = 'EUR'),
     'DE89370400440532013000', 500.00, 'deposit', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 2. Withdrawal Transaction (Bob withdraws money from his EUR wallet to Bank B)
INSERT INTO transaction (id, sender_wallet_id, recipient_wallet_id, iban, amount, transaction_type, transaction_status, created_at, updated_at) VALUES
    (uuid_generate_v4(), (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'bob.smith@example.com') AND currency = 'EUR'),
     NULL, 'GB29NWBK60161331926819', 1000.00, 'withdrawal', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 3. Transfer Transaction (Charlie transfers from CZK wallet to Diana's EUR wallet)
-- No IBAN involved in this transfer (wallet-to-wallet)
INSERT INTO transaction (id, sender_wallet_id, recipient_wallet_id, iban, amount, transaction_type, transaction_status, created_at, updated_at) VALUES
    (uuid_generate_v4(), (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'charlie.brown@example.com') AND currency = 'CZK'),
     (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'diana.prince@example.com') AND currency = 'EUR'),
     NULL, 200.00, 'transfer', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 4. Transfer Transaction (Eve transfers from CZK wallet to Bob's CZK wallet)
-- No IBAN involved in this transfer (wallet-to-wallet)
INSERT INTO transaction (id, sender_wallet_id, recipient_wallet_id, iban, amount, transaction_type, transaction_status, created_at, updated_at) VALUES
    (uuid_generate_v4(), (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'eve.adams@example.com') AND currency = 'CZK'),
     (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'bob.smith@example.com') AND currency = 'CZK'),
     NULL, 1500.00, 'transfer', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 5. Deposit Transaction (Diana deposits money from Bank D to her EUR wallet)
INSERT INTO transaction (id, sender_wallet_id, recipient_wallet_id, iban, amount, transaction_type, transaction_status, created_at, updated_at) VALUES
    (uuid_generate_v4(), NULL, (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'diana.prince@example.com') AND currency = 'EUR'),
     'IT60X0542811101000000123456', 1000.00, 'deposit', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 6. Withdrawal Transaction (Alice withdraws money from her CZK wallet to Bank E)
INSERT INTO transaction (id, sender_wallet_id, recipient_wallet_id, iban, amount, transaction_type, transaction_status, created_at, updated_at) VALUES
    (uuid_generate_v4(), (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'alice.johnson@example.com') AND currency = 'CZK'),
     NULL, 'FR7630006000011234567890189', 350.00, 'withdrawal', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 7. Transfer Transaction (Bob transfers from EUR wallet to Charlie's CZK wallet)
-- No IBAN involved in this transfer (wallet-to-wallet)
INSERT INTO transaction (id, sender_wallet_id, recipient_wallet_id, iban, amount, transaction_type, transaction_status, created_at, updated_at) VALUES
    (uuid_generate_v4(), (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'bob.smith@example.com') AND currency = 'EUR'),
     (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'charlie.brown@example.com') AND currency = 'CZK'),
     NULL, 1000.00, 'transfer', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 8. Deposit Transaction (Eve deposits money from Bank F to her EUR wallet)
INSERT INTO transaction (id, sender_wallet_id, recipient_wallet_id, iban, amount, transaction_type, transaction_status, created_at, updated_at) VALUES
    (uuid_generate_v4(), NULL, (SELECT id FROM wallet WHERE user_id = (SELECT id FROM "user" WHERE email = 'eve.adams@example.com') AND currency = 'EUR'),
     'GB11NWBK60161331926820', 800.00, 'deposit', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);