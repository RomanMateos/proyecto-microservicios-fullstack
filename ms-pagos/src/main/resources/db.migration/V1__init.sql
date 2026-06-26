CREATE TABLE IF NOT EXISTS PAGO (
                                    id          INT AUTO_INCREMENT PRIMARY KEY,
                                    metodo_pago VARCHAR(50)  NOT NULL,
    monto       DOUBLE       NOT NULL,
    estado_pago VARCHAR(30)  NOT NULL,
    fecha_pago  DATE         NOT NULL,
    pagado      BOOLEAN      NOT NULL DEFAULT FALSE,
    pedido_id   INT          NOT NULL
    );