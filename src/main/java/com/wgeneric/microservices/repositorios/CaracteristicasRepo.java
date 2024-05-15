package com.wgeneric.microservices.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 import com.wgeneric.microservices.models.entidades.Caracteristicas;

@Repository
public interface CaracteristicasRepo extends JpaRepository<Caracteristicas, Integer> {

}
