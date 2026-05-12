package com.fullstackev2.usuarios.repository;

import com.fullstackev2.usuarios.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil,Integer> {
}
