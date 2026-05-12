package com.fullstackev2.productos.runner;

import com.fullstackev2.productos.model.Categoria;
import com.fullstackev2.productos.model.Producto;
import com.fullstackev2.productos.repository.CategoriaRepository;
import com.fullstackev2.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(2)
public class ProductoRunner implements CommandLineRunner {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Override
    public void run(String... args) throws Exception{
        Categoria categoria1 = categoriaRepository.findById(1).orElse(null);
        Categoria categoria2 = categoriaRepository.findById(2).orElse(null);
        Categoria categoria3 = categoriaRepository.findById(3).orElse(null);

    if(!productoRepository.existsById(1)){
        productoRepository.save(new Producto(null,"Shampoo","Shampoo H&S para la caspa",12590.0,12, LocalDate.of(2028,6,5),true,categoria1));
    }
    if(!productoRepository.existsById(2)){
        productoRepository.save(new Producto(null,"Fideos","Fideos marca Luchetti",1990.0,15,LocalDate.of(2027,4,5),true,categoria2));
    }
    if(!productoRepository.existsById(3)){
        productoRepository.save(new Producto(null,"Merluza Apanada","Merluza congelada marca nuestro mar",15990.0,3,LocalDate.of(2028,6,5),true,categoria3));
    }
    System.out.println("Productos cargadas correctamente");
}}
