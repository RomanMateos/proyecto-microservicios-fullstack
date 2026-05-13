package com.fullstackev2.envios.repository;

import com.fullstackev2.envios.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<Envio,Integer> {

        @Query("SELECT DISTINCT e FROM Envio e " +
                "JOIN e.seguimientos s " +
                "WHERE e.fechaEnvio BETWEEN :fechaInicio AND :fechaFin " +
                "AND e.activo = true " +
                "AND s.entregado = false ")
        List<Envio> buscarEnviosNoEntregadosPorRangoFechas(
                @Param("fechaInicio") LocalDate fechaInicio,
                @Param("fechaFin") LocalDate fechaFin
        );

}
