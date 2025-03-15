CREATE TABLE IF NOT EXISTS "user" (
                                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                      name VARCHAR(255) NOT NULL,
                                      surname VARCHAR(255) NOT NULL,
                                      email CITEXT NOT NULL UNIQUE,
                                      password VARCHAR(255) NOT NULL,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_user_email ON "user" (email);