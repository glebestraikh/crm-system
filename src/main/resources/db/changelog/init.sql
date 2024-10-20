CREATE TABLE seller
(
    id         BIGSERIAL PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    contact_info      VARCHAR(255) NOT NULL,
    registration_date TIMESTAMP    NOT NULL
);

CREATE TABLE transaction
(
    id   BIGSERIAL PRIMARY KEY,
    seller_id        BIGINT       NOT NULL,
    amount           INTEGER      NOT NULL,
    payment_type     VARCHAR(255) NOT NULL,
    transaction_date TIMESTAMP    NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES seller (id)
);