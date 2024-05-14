package com.wgeneric.microservices.services;

import org.springframework.stereotype.Service;

import com.wgeneric.microservices.models.multiservice.RequestMS;
import com.wgeneric.microservices.models.multiservice.ResponseMS;
import com.wgeneric.microservices.services.imp.operaciones.Consulta;
import com.wgeneric.microservices.services.imp.operaciones.Extorno;
import com.wgeneric.microservices.services.imp.operaciones.Pago;
import com.wgeneric.microservices.services.interfaces.Operacion;

@Service
public class OperacionFacade {
    private Operacion operacion;

    private String protocolType = "CONSULTA";
    
    public void setProtocolType(String protocolType) {
    	this.protocolType = protocolType;
    }
    
    public OperacionFacade() {
        if (protocolType.equalsIgnoreCase("CONSULTA")) {
            operacion = new Consulta();
        } else if (protocolType.equalsIgnoreCase("PAGO")) {
            operacion = new Pago();
        } else if (protocolType.equalsIgnoreCase("EXTORNO")) {
            operacion = new Extorno();
        }
    }

    public ResponseMS sendRequest(RequestMS request) {
        return operacion.operacionEntidad(request);
    }

}
