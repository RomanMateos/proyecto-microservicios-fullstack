package com.fullstackev2.reportes.repository;

import com.fullstackev2.reportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {
}