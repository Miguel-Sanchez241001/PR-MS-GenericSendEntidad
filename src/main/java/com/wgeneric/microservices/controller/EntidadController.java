package com.wgeneric.microservices.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wgeneric.microservices.models.entidades.Entidad;
import com.wgeneric.microservices.repositorios.EntidadRepo;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/entidad")
public class EntidadController {
    
    @Autowired
    public EntidadRepo entidadRepo;




    // Método para guardar una entidad
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entidad> guardarEntidad(@Valid @RequestBody Entidad entidad) {
        Entidad nuevaEntidad = entidadRepo.save(entidad);
        return ResponseEntity.status(HttpStatus.OK).body(nuevaEntidad);
    }

    // Método para buscar una entidad por su ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entidad> buscarPorId(@PathVariable String id) {
        Optional<Entidad> entidadOptional = entidadRepo.findById(id);
        return entidadOptional.map(entidad -> ResponseEntity.ok().body(entidad))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para actualizar una entidad existente
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entidad> actualizarEntidad(@PathVariable String id, @Valid @RequestBody Entidad entidadActualizada) {
        if (!entidadRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entidadActualizada.setId(id);
        Entidad entidadGuardada = entidadRepo.save(entidadActualizada);
        return ResponseEntity.ok(entidadGuardada);
    }

    // Método para listar todas las entidades
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Entidad>> listarEntidades() {
        List<Entidad> entidades = entidadRepo.findAll();
        return ResponseEntity.ok().body(entidades);
    }

    // Método para buscar entidades por un filtro (por ejemplo, nombre)
    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Entidad>> buscarPorFiltro(@RequestParam String filtro) {
        List<Entidad> entidades = entidadRepo.findByNombre(filtro);
        return ResponseEntity.ok().body(entidades);
    }
}
