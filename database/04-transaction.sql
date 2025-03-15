CREATE TABLE IF NOT EXISTS transaction (
                                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                           sender_wallet_id UUID REFERENCES wallet (id) ON DELETE CASCADE,
                                           recipient_wallet_id UUID REFERENCES wallet (id) ON DELETE CASCADE,
                                           iban VARCHAR(34),
                                           amount DECIMAL(10, 2) NOT NULL,
                                           transaction_type transaction_type NOT NULL,
                                           transaction_status transaction_status NOT NULL,
                                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);