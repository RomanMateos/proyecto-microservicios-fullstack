package com.fullstackev2.reportes.repository;

import com.fullstackev2.reportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    @Query("SELECT r FROM Reporte r WHERE r.activo = true ORDER BY r.fechaGeneracion DESC")
    List<Reporte> findReportesActivos();
}