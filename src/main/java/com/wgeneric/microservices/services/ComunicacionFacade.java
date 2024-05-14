package com.wgeneric.microservices.services;

import org.springframework.stereotype.Service;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.services.imp.RestProtocol;
import com.wgeneric.microservices.services.interfaces.CommunicationProtocol;

@Service
public class ComunicacionFacade {
        private CommunicationProtocol protocol;

         public ComunicacionFacade(String protocolType) {
        if (protocolType.equalsIgnoreCase("REST")) {
            protocol = new RestProtocol();
        } else if (protocolType.equalsIgnoreCase("SOAP")) {
         //   protocol = new SoapProtocol();
        } else if (protocolType.equalsIgnoreCase("MQ")) {
       //     protocol = new MqProtocol();
        }
    }


    public ResponseEntidad sendRequest(RequestEntidad request) {
       return protocol.sendRequest(request);
    }


}
