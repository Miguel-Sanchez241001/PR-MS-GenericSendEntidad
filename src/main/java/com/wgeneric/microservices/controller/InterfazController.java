package com.wgeneric.microservices.controller;

import com.wgeneric.microservices.models.entidades.Entidad;
import com.wgeneric.microservices.models.entidades.Interfaces;

import com.wgeneric.microservices.repositorios.EntidadRepo;
import com.wgeneric.microservices.repositorios.InterfazRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interfaz")
public class InterfazController {

    @Autowired
    public InterfazRepo interfazRepo;
    @Autowired
    public EntidadRepo entidadRepo;


    // Método para guardar una interfaz
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Interfaces> guardarInterfaz(@Valid @RequestBody Interfaces interfaz) {
        // Verificar si la entidad existe
        Entidad entidad = entidadRepo.findById(interfaz.getEntidadId()).orElse(null);
        if (entidad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        interfaz.setEntidad(entidad);
        Interfaces nuevaInterfaz = interfazRepo.save(interfaz);
        return ResponseEntity.status(HttpStatus.OK).body(nuevaInterfaz);
    }

    // Método para actualizar una interfaz existente
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Interfaces> actualizarInterfaz(@PathVariable Integer id, @Valid @RequestBody Interfaces interfazActualizada) {
        if (!interfazRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        interfazActualizada.setId(id);
        Interfaces interfazGuardada = interfazRepo.save(interfazActualizada);
        return ResponseEntity.status(HttpStatus.OK).body(interfazGuardada);
    }

    // Método para listar todas las interfaces
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Interfaces>> listarInterfaces() {
        List<Interfaces> interfaces = interfazRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(interfaces);
    }

    // Método para buscar interfaces por un filtro (por ejemplo, endpoint)
    @GetMapping("/buscar")
    public ResponseEntity<List<Interfaces>> buscarPorFiltro(@RequestParam String filtro) {
        // Implementa la lógica de búsqueda según tus necesidades
        List<Interfaces> interfaces = interfazRepo.findByOperationType(filtro);
        return ResponseEntity.status(HttpStatus.OK).body(interfaces);
    }
}
