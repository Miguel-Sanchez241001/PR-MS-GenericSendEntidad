package com.wgeneric.microservices.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wgeneric.microservices.models.entidades.Entidad;
 
@Repository
public interface EntidadRepo  extends JpaRepository<Entidad, String> {

}
