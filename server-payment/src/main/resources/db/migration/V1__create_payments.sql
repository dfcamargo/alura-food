CREATE TABLE payments (
    id UUID NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    name VARCHAR(100) DEFAULT NULL,
    registration VARCHAR(19) DEFAULT NULL,
    expiration VARCHAR(7) NOT NULL,
    code VARCHAR(3) DEFAULT NULL,
    status VARCHAR(255) NOT NULL,
    order_id UUID NOT NULL,
    payment_method_id UUID NOT NULL,
    PRIMARY KEY (id)
)