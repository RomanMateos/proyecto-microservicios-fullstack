package com.fullstackev2.usuarios.runner;

import com.fullstackev2.usuarios.model.Usuario;
import com.fullstackev2.usuarios.repository.PerfilRepository;
import com.fullstackev2.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
@Order(1)
public class UsuarioRunner implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public void run(String... args) throws Exception {
        if(!usuarioRepository.existsById(1)){
            usuarioRepository.save(new Usuario(null,"Pedro Pascal Montolla", "pedro.pascal@gmail.com","Las acacias #123",35, LocalDate.of(1991,2,15),true, new ArrayList<>()));

        }
        if(!usuarioRepository.existsById(2)){
            usuarioRepository.save(new Usuario(null,"Ana Banana Lopez","manzana.ana@gmail.com","perulandia #321",20, LocalDate.of(2005,5,29),true,new ArrayList<>()));
        }
        if(!usuarioRepository.existsById(3)){
            usuarioRepository.save(new Usuario(null,"Carlos","carlosbkn@gmail.com","av. bonilla #224",31,LocalDate.of(1995,5,4),true,new ArrayList<>()));
        }
        System.out.println("Usuarios cargados correctamente");
    }
}
