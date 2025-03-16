CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS citext;

-- Currency Enum
CREATE TYPE currency_type AS ENUM ('EUR', 'CZK');

-- Transaction Type Enum
CREATE TYPE transaction_type AS ENUM (
    'DEPOSIT',
    'WITHDRAWAL',
    'TRANSFER'
    );

-- Transaction Status Enum
CREATE TYPE transaction_status AS ENUM (
    'PENDING',
    'COMPLETED',
    'FAILED'
    );

