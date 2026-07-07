CREATE TABLE IF NOT EXISTS PEDIDO (
                                      id               INT AUTO_INCREMENT PRIMARY KEY,
                                      estado           VARCHAR(50)  NOT NULL,
    direccion_entrega VARCHAR(200) NOT NULL,
    total            DOUBLE       NOT NULL,
    pagado           BOOLEAN      NOT NULL DEFAULT FALSE,
    fecha_pedido     DATE         NOT NULL,
    usuario_id       INT
    );

CREATE TABLE IF NOT EXISTS DETALLE_PEDIDO (
                                              id               INT AUTO_INCREMENT PRIMARY KEY,
                                              nombre_producto  VARCHAR(100) NOT NULL,
    cantidad         INT          NOT NULL,
    precio_unitario  DOUBLE       NOT NULL,
    activo           BOOLEAN      NOT NULL DEFAULT TRUE,
    fecha_agregado   DATE         NOT NULL,
    producto_id      INT,
    pedido_id        INT,
    FOREIGN KEY (pedido_id) REFERENCES PEDIDO(id)
    );