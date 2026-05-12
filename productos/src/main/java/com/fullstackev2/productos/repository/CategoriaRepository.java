package com.fullstackev2.productos.repository;

import com.fullstackev2.productos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {
}
