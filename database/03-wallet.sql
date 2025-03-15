CREATE TABLE IF NOT EXISTS wallet (
                                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                      user_id UUID NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
                                      currency currency_type NOT NULL, -- Table for currencies should be use if more types would be added in future
                                      balance DECIMAL(10, 2) NOT NULL DEFAULT 0,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);