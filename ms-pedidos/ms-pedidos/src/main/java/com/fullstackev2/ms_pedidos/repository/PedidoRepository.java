package com.fullstackev2.ms_pedidos.repository;

import com.fullstackev2.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // JPQL — pedidos pagados ordenados del más reciente al más antiguo
    @Query("SELECT p FROM Pedido p WHERE p.pagado = true ORDER BY p.fechaPedido DESC")
    List<Pedido> findPedidosPagadosOrdenados();
}