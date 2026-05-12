package com.fullstackev2.inventario.repository;

import com.fullstackev2.inventario.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoStockRepository extends JpaRepository<MovimientoStock,Integer> {
}
