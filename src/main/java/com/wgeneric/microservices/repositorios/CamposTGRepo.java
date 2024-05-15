package com.wgeneric.microservices.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wgeneric.microservices.models.entidades.CamposTG;
 
@Repository
public interface CamposTGRepo extends JpaRepository<CamposTG, Integer> {

}
