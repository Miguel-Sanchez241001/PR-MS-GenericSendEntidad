package com.wgeneric.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgeneric.microservices.models.multiservice.RequestMS;
import com.wgeneric.microservices.models.multiservice.ResponseMS;
import com.wgeneric.microservices.services.OperacionFacade;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1" )
public class OperacionesController {


    @Autowired
    private OperacionFacade operationsEntities;

    @PostConstruct
    void init(){
        System.out.println("INICIO CONTROLLER GENERICO");
    }

    @PostMapping("/consulta")
    public ResponseMS ConsultaEntidad(@RequestBody RequestMS entity) {

         return operationsEntities.sendRequest(entity);    
    }
    
    @PostMapping("/pago")
    public ResponseMS PagoEntidad(@RequestBody RequestMS entity) {
        return operationsEntities.sendRequest(entity);    

    }
    
    @PostMapping("/extorno")
    public ResponseMS ExtornoEntidad(@RequestBody RequestMS entity) {
        return operationsEntities.sendRequest(entity);    

    }



}
