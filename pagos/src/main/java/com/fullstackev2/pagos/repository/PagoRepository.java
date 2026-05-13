package com.fullstackev2.pagos.repository;

import com.fullstackev2.pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago,Integer> {
}
