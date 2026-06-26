package com.fullstackev2.ms_pedidos.repository;

import com.fullstackev2.ms_pedidos.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
}