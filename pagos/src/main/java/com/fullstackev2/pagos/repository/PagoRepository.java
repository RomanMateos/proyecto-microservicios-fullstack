package com.fullstackev2.pagos.repository;

import com.fullstackev2.pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago,Integer> {

    @Query
            ("SELECT p FROM Pago p " +
            "WHERE p.montoPago > :monto " +
            "AND p.aceptado = :aceptado ")
    List<Pago> buscarPagosPorMontoYEstado(@Param("monto") Double monto,
                                          @Param("aceptado")Boolean aceptado
    );
}
