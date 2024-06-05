package com.wgeneric.microservices.repositorios;

import com.wgeneric.microservices.models.entidades.Interfaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wgeneric.microservices.models.entidades.Plantilla;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantillaRepo extends JpaRepository<Plantilla, Integer> {

 List<Plantilla> findByPlantillaType(String PlantillaType);
 List<Plantilla> findByInterfaces(Interfaces interfaces);
}
