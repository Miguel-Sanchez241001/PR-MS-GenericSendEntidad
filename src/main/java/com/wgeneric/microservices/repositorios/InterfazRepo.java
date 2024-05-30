package com.wgeneric.microservices.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wgeneric.microservices.models.entidades.Interfaces;

import java.util.List;

@Repository
public interface InterfazRepo extends JpaRepository<Interfaces, Integer> {
 List<Interfaces> findByOperationType(String operador);
}
