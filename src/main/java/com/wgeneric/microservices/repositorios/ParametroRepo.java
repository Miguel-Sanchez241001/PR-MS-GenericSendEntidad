package com.wgeneric.microservices.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wgeneric.microservices.models.entidades.Parametros;

import java.util.List;

@Repository
public interface ParametroRepo extends JpaRepository<Parametros, Integer> {

    List<Parametros> findByCaracteristicaId(Integer id);
}
