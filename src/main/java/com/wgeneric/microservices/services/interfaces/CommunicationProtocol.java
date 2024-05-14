package com.wgeneric.microservices.services.interfaces;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;

public interface CommunicationProtocol {
     
    public ResponseEntidad sendRequest(RequestEntidad requestEntidad);
}
