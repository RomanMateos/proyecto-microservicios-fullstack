package com.fullstackev2.productos.runner;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fullstackev2.productos.model.Categoria;
import com.fullstackev2.productos.model.Producto;
import com.fullstackev2.productos.repository.CategoriaRepository;
import com.fullstackev2.productos.service.CategoriaService;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;


@Component
@Order(1)
public class CategoriaRunner implements CommandLineRunner {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!categoriaRepository.existsById(1)) {
            categoriaRepository.save(new Categoria(null, "Aseo", "Articulos de limpieza de primera necesidad", true, 25, LocalDate.of(2020, 5, 5), new ArrayList<>()));
        }
        if (!categoriaRepository.existsById(2)) {
            categoriaRepository.save(new Categoria(null,"Abarrotes","Legumbres, Granos, Cereales",true,120,LocalDate.of(2020,5,5),new ArrayList<>()));

        }
        if (!categoriaRepository.existsById(3)) {
            categoriaRepository.save(new Categoria(null,"Congelados","Carnes, Pollo, Pescados",true,1234,LocalDate.of(2020,5,5),new ArrayList<>()));
        }
        System.out.println("Categorias cargadas correctamente");
    }
}