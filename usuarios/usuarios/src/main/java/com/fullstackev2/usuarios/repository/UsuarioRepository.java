package com.fullstackev2.usuarios.repository;

import com.fullstackev2.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    List<Usuario> findByEmailAndActivo(String email, Boolean activo);
}
