package com.wgeneric.microservices.services;

import org.springframework.stereotype.Service;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.services.imp.RestProtocol;
import com.wgeneric.microservices.services.interfaces.CommunicationProtocol;

import java.io.IOException;


public class ComunicacionFacade {
        private CommunicationProtocol protocol;

 

    public void setComunicacionFacade(String protocolType) {
        if (protocolType.equalsIgnoreCase("REST")) {
            protocol = new RestProtocol();
        } else if (protocolType.equalsIgnoreCase("SOAP")) {
         //   protocol = new SoapProtocol();
        } else if (protocolType.equalsIgnoreCase("MQ")) {
       //     protocol = new MqProtocol();
        }
    }

    public ResponseEntidad sendRequest(RequestEntidad request) throws IOException, InterruptedException {
       return protocol.sendRequest(request);
    }


}
