package com.fullstackev2.sucursales.repository;

import com.fullstackev2.sucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    @Query(value = "SELECT s.* FROM sucursales s INNER JOIN regiones r ON s.region_id = r.region_id WHERE r.nombre = :nombreRegion", nativeQuery = true)
    List<Sucursal> findByNombreRegion(@Param("nombreRegion") String nombreRegion);
}