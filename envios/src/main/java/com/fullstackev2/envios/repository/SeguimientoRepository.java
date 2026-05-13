package com.fullstackev2.envios.repository;

import com.fullstackev2.envios.model.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento,Integer> {
}
