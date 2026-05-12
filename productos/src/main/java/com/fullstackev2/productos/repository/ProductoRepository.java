package com.fullstackev2.productos.repository;

import com.fullstackev2.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    List<Producto> findByNombreProductoContainingIgnoreCaseAndPrecioLessThan(String nombreProducto,Double precio);
}
