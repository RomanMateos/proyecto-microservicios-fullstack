INSERT INTO PEDIDO (estado, direccion_entrega, total, pagado, fecha_pedido, usuario_id)
VALUES ('pendiente', 'Av. Siempre Viva 123, Santiago', 59990.0, false, '2025-01-10', 1);

INSERT INTO PEDIDO (estado, direccion_entrega, total, pagado, fecha_pedido, usuario_id)
VALUES ('pagado', 'Calle Los Andes 456, Valparaíso', 129990.0, true, '2025-02-15', 2);

INSERT INTO PEDIDO (estado, direccion_entrega, total, pagado, fecha_pedido, usuario_id)
VALUES ('enviado', 'Pasaje Central 789, Concepción', 34990.0, true, '2025-03-20', 3);

INSERT INTO DETALLE_PEDIDO (nombre_producto, cantidad, precio_unitario, activo, fecha_agregado, producto_id, pedido_id)
VALUES ('Laptop Lenovo', 1, 59990.0, true, '2025-01-10', 1, 1);

INSERT INTO DETALLE_PEDIDO (nombre_producto, cantidad, precio_unitario, activo, fecha_agregado, producto_id, pedido_id)
VALUES ('Monitor Samsung', 2, 49990.0, true, '2025-02-15', 2, 2);

INSERT INTO DETALLE_PEDIDO (nombre_producto, cantidad, precio_unitario, activo, fecha_agregado, producto_id, pedido_id)
VALUES ('Teclado Mecánico', 1, 34990.0, true, '2025-03-20', 3, 3);