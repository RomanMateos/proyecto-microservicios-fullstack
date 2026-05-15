package com.fullstackev2.proveedores.repository;

import com.fullstackev2.proveedores.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM proveedores WHERE activo = true ORDER BY nombre ASC", nativeQuery = true)
    List<Proveedor> findAllActivosOrdenados();
}