package com.fullstackev2.usuarios.runner;

import com.fullstackev2.usuarios.model.Perfil;
import com.fullstackev2.usuarios.model.Usuario;
import com.fullstackev2.usuarios.repository.PerfilRepository;

import com.fullstackev2.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(2)
public class PerfilRunner implements CommandLineRunner {
    @Autowired
    PerfilRepository perfilRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Override
    public void run(String... args) throws Exception {
        Usuario usuario1 = usuarioRepository.findById(1).orElse(null);
        Usuario usuario2 = usuarioRepository.findById(2).orElse(null);
        Usuario usuario3 = usuarioRepository.findById(3).orElse(null);
        if(!perfilRepository.existsById(1)){
            perfilRepository.save(new Perfil(null,"Pedritop","Pedro#36945","pedro.pascal@gmail.com",35, LocalDate.of(1991,2,15),true,usuario1));
        }
        if(!perfilRepository.existsById(2)){
            perfilRepository.save(new Perfil(null,"Anita","Manzanita#28312","manzana.ana@gmail.com",20, LocalDate.of(2005,5,29),true,usuario2));
        }
        if(!perfilRepository.existsById(3)){
            perfilRepository.save(new Perfil(null,"Carlos","Carlitros#29328","carlosbkn@gmail.com",31,LocalDate.of(1995,4,5),true,usuario3));
        }
        System.out.println("Perfiles cargados");
    }
}
