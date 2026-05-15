package com.fullstackev2.empleados.repository;

import com.fullstackev2.empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM empleados WHERE sucursal_id = :sucursalId AND YEAR(fecha_ingreso) = :anio", nativeQuery = true)
    List<Empleado> findBySucursalIdAndAnioIngreso(@Param("sucursalId") Integer sucursalId, @Param("anio") Integer anio);
}