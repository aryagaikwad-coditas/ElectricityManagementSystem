CREATE TABLE IF NOT EXISTS master.clients (
    id                  BIGSERIAL PRIMARY KEY,
    company_name        VARCHAR(255) NOT NULL UNIQUE,
    schema_name         VARCHAR(100) NOT NULL UNIQUE,
    email               VARCHAR(255) NOT NULL UNIQUE,
    phone               VARCHAR(20)  NOT NULL,
    address             TEXT         NOT NULL,
    client_poc_name     VARCHAR(255),
    client_poc_email    VARCHAR(255),
    client_poc_phone    VARCHAR(20),
    status              VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    onboarded_by        BIGINT REFERENCES master.users(id),
    created_at          TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS master.client_assignments (
    id              BIGSERIAL PRIMARY KEY,
    sales_user_id   BIGINT NOT NULL REFERENCES master.users(id),
    client_id       BIGINT NOT NULL REFERENCES master.clients(id),
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    assigned_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (sales_user_id, client_id)
    );