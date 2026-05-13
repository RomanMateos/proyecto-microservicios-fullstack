package com.fullstackev2.inventario.repository;

import com.fullstackev2.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario,Integer> {
    List<Inventario> findByCantidadDisponibleGreaterThanAndActivoTrue(Integer cantidadDisponibles);
}
