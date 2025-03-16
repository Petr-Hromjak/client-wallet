CREATE TABLE IF NOT EXISTS transaction (
                                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                           sender_wallet_id UUID REFERENCES wallet (id) ON DELETE SET NULL,
                                           receiver_wallet_id UUID REFERENCES wallet (id) ON DELETE SET NULL,
                                           account_number VARCHAR(18) CHECK (LENGTH(account_number) BETWEEN 10 AND 18),
                                           bank_code VARCHAR(6) CHECK (LENGTH(bank_code) BETWEEN 4 AND 6),
                                           amount DECIMAL(10, 2) NOT NULL,
                                           currency currency_type NOT NULL, -- Table for currencies should be use if more types would be added in future
                                           transaction_type transaction_type NOT NULL,
                                           transaction_status transaction_status NOT NULL,
                                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_transaction_sender_wallet ON transaction (sender_wallet_id);
CREATE INDEX idx_transaction_receiver_wallet ON transaction (receiver_wallet_id);

CREATE OR REPLACE FUNCTION delete_null_transactions()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.sender_wallet_id IS NULL AND NEW.receiver_wallet_id IS NULL THEN
        DELETE FROM transaction WHERE id = NEW.id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_null_transactions_trigger
    AFTER INSERT OR UPDATE ON transaction
    FOR EACH ROW EXECUTE FUNCTION delete_null_transactions();