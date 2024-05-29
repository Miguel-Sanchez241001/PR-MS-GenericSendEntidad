package com.wgeneric.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgeneric.microservices.models.entidades.Entidad;
import com.wgeneric.microservices.repositorios.EntidadRepo;


@RestController
@RequestMapping("/api/entidad")
public class EntidadController {
    
    @Autowired
    public EntidadRepo entidadRepo;
    @PostMapping
    public Entidad postMethodName(@RequestBody Entidad entidad) {
        
        return entidadRepo.save(entidad);
    }
    
}
