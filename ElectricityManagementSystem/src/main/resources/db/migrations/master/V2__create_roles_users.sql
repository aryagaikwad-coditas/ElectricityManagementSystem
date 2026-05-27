CREATE TABLE IF NOT EXISTS master.roles (
      id      BIGSERIAL PRIMARY KEY,
      name    VARCHAR(50) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS master.users (
    id          BIGSERIAL PRIMARY KEY,
    full_name   VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    phone       VARCHAR(20)  NOT NULL,
    created_by  BIGINT REFERENCES master.users(id),
    active      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS master.user_roles (
    user_id BIGINT NOT NULL REFERENCES master.users(id),
    role_id BIGINT NOT NULL REFERENCES master.roles(id),
    PRIMARY KEY (user_id, role_id)
    );


INSERT INTO master.roles (name) VALUES
        ('OWNER'),
        ('MANAGEMENT'),
        ('SALES'),
        ('CRM')
    ON CONFLICT DO NOTHING;
