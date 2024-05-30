package com.wgeneric.microservices.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wgeneric.microservices.models.entidades.Entidad;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntidadRepo  extends JpaRepository<Entidad, String> {
    List<Entidad> findByNombre(String nombre);
}
