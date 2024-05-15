package com.wgeneric.microservices.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wgeneric.microservices.models.entidades.Parametros;

@Repository
public interface ParametroRepo extends JpaRepository<Parametros, Integer> {

}
