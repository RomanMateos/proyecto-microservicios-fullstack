package com.fullstackev2.ms_pagos.repository;

import com.fullstackev2.ms_pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    @Query("SELECT p FROM Pago p WHERE p.monto > :monto AND p.estadoPago = :estado")
    List<Pago> obtenerPorMontoYEstado(@Param("monto") Double monto,
                                      @Param("estado") String estado);
}