package com.wgeneric.microservices.controller;

import com.wgeneric.microservices.models.entidades.Caracteristicas;
import com.wgeneric.microservices.models.entidades.Entidad;
import com.wgeneric.microservices.models.entidades.Interfaces;
import com.wgeneric.microservices.models.entidades.Parametros;
import com.wgeneric.microservices.repositorios.CaracteristicasRepo;
import com.wgeneric.microservices.repositorios.InterfazRepo;
import com.wgeneric.microservices.repositorios.ParametroRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parametros")
public class ParametrosController {

    @Autowired
    public InterfazRepo interfazRepo;

    @Autowired
    public ParametroRepo parametroRepo;
    @Autowired
    public CaracteristicasRepo caracteristicasRepo;


    // Método para guardar un parámetro
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parametros> guardarParametro(@Valid @RequestBody Parametros parametro) {
        Interfaces interfaces = interfazRepo.findById(parametro.getInterfazId()).orElseThrow();
        parametro.setInterfaces(interfaces);
        Caracteristicas caracteristica = caracteristicasRepo.findById(parametro.getCaracteristicaId()).orElseThrow();
        parametro.setCaracteristicas(caracteristica);
        Parametros nuevoParametro = parametroRepo.save(parametro);
        return ResponseEntity.status(HttpStatus.OK).body(nuevoParametro);
    }

    // Método para listar todos los parámetros
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Parametros>> listarParametros() {
        List<Parametros> parametros = parametroRepo.findAll();
        if (parametros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(parametros);
    }

    // Método para obtener un parámetro por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parametros> obtenerParametroPorId(@PathVariable Integer id) {
        Optional<Parametros> parametroOptional = parametroRepo.findById(id);
        return parametroOptional.map(parametro -> ResponseEntity.ok().body(parametro))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para obtener parámetros por filtro (aquí asumo que el filtro es un parámetro llamado 'filtro')
    @GetMapping(value = "/filtro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Parametros>> obtenerParametrosPorFiltro(@RequestParam("filtro") Integer filtro) {
        List<Parametros> parametros = parametroRepo.findByCaracteristicaId(filtro); // Reemplaza con el método correspondiente
        if (parametros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(parametros);
    }

    // Método para actualizar un parámetro por ID
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parametros> actualizarParametro(@PathVariable Integer id, @Valid @RequestBody Parametros parametro) {
        if (!parametroRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        parametro.setId(id); // Asegurando que el parámetro tenga el ID correcto
        Interfaces interfaces = interfazRepo.findById(parametro.getInterfazId()).orElseThrow();
        parametro.setInterfaces(interfaces);
        Caracteristicas caracteristica = caracteristicasRepo.findById(parametro.getCaracteristicaId()).orElseThrow();
        parametro.setCaracteristicas(caracteristica);
        Parametros updatedParametro = parametroRepo.save(parametro);
        return ResponseEntity.ok().body(updatedParametro);
    }
}
