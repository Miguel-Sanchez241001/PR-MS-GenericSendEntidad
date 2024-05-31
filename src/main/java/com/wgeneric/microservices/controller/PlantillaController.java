package com.wgeneric.microservices.controller;

import com.wgeneric.microservices.models.entidades.Entidad;
import com.wgeneric.microservices.models.entidades.Interfaces;
import com.wgeneric.microservices.models.entidades.Plantilla;
import com.wgeneric.microservices.repositorios.EntidadRepo;
import com.wgeneric.microservices.repositorios.InterfazRepo;
import com.wgeneric.microservices.repositorios.PlantillaRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plantilla")
public class PlantillaController {


    @Autowired
    public InterfazRepo interfazRepo;

    @Autowired
    public EntidadRepo entidadRepo;

    @Autowired
    public PlantillaRepo plantillaRepo;



    // Método para guardar una entidad
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Plantilla> guardarPlantiila(@Valid @RequestBody Plantilla newplan) {
        Interfaces interfaz = interfazRepo.findById(newplan.getInterfazId()).get();
        newplan.setInterfaces(interfaz);
        Plantilla plantilla = plantillaRepo.save(newplan);
        return ResponseEntity.status(HttpStatus.OK).body(plantilla);
    }


    // Método para obtener una plantilla por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Plantilla> obtenerPlantillaPorId(@PathVariable Integer id) {
        Optional<Plantilla> plantillaOptional = plantillaRepo.findById(id);
        return plantillaOptional.map(plantilla -> ResponseEntity.ok().body(plantilla))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para obtener una plantilla por filtro (aquí asumo que el filtro es un parámetro llamado 'filtro')
    @GetMapping(value = "/filtro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Plantilla>> obtenerPlantillasPorFiltro(@RequestParam("filtro") String filtro) {
        List<Plantilla> plantillas = plantillaRepo.findByPlantillaType(filtro); // Reemplaza con el método correspondiente
        if (plantillas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(plantillas);
    }

    // Método para listar todas las plantillas
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Plantilla>> listarPlantillas() {
        List<Plantilla> plantillas = plantillaRepo.findAll();
        if (plantillas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(plantillas);
    }

    // Método para actualizar una plantilla por ID
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Plantilla> actualizarPlantilla(@PathVariable Integer id, @Valid @RequestBody Plantilla plantilla) {
        if (!plantillaRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        plantilla.setId(id); // Asegurando que la plantilla tenga el ID correcto
        Interfaces interfaz = interfazRepo.findById(plantilla.getInterfazId()).orElseThrow(); // Puedes manejar la excepción adecuadamente
        plantilla.setInterfaces(interfaz);
        Plantilla updatedPlantilla = plantillaRepo.save(plantilla);
        return ResponseEntity.ok().body(updatedPlantilla);
    }

}
