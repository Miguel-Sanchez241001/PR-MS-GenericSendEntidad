package com.wgeneric.microservices.services;

import com.wgeneric.microservices.repositorios.EntidadRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wgeneric.microservices.models.multiservice.RequestMS;
import com.wgeneric.microservices.models.multiservice.ResponseMS;
import com.wgeneric.microservices.services.imp.operaciones.Consulta;
import com.wgeneric.microservices.services.imp.operaciones.Extorno;
import com.wgeneric.microservices.services.imp.operaciones.Pago;
import com.wgeneric.microservices.services.interfaces.Operacion;

import java.io.IOException;

@Service
public class OperacionFacade {
    private Operacion operacion;

    private String protocolType = "CONSULTA";
    @Autowired
    private  TramaService tramaService;
    @Autowired
    private EntidadRepo entidadRepo;




    public void setProtocolType(String protocolType) {
    	this.protocolType = protocolType;
    }

    @PostConstruct
    void init(){
        System.out.println("INICIO OperacionFacade");
    }



    @Autowired
    public OperacionFacade(Consulta consulta) {
        if (protocolType.equalsIgnoreCase("CONSULTA")) {
            operacion = consulta;
        } else if (protocolType.equalsIgnoreCase("PAGO")) {
            operacion = new Pago();
        } else if (protocolType.equalsIgnoreCase("EXTORNO")) {
            operacion = new Extorno();
        }
    }

    public ResponseMS sendRequest(RequestMS request) throws IOException, InterruptedException {
        return operacion.operacionEntidad(request);
    }

}
