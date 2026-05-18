package com.fullstackev2.sucursales.repository;

import com.fullstackev2.sucursales.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    @Query(value = "SELECT * FROM regiones WHERE nombre = :nombre", nativeQuery = true)
    List<Region> findByNombre(@Param("nombre") String nombre);
}