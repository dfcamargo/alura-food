CREATE TABLE order_items (
  id BIGINT NOT NULL,
  description VARCHAR(255) DEFAULT NULL,
  quantity INTEGER NOT NULL,
  order_id UUID NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES orders(id)
)