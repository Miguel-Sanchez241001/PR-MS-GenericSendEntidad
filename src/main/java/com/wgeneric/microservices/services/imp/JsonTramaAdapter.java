package com.wgeneric.microservices.services.imp;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.services.interfaces.TramaAdapter;

public class JsonTramaAdapter implements TramaAdapter {

    private RequestEntidad requestEntidad;
    public JsonTramaAdapter(RequestEntidad requestEntidad) {
        this.requestEntidad = requestEntidad;
    }

    @Override
    public RequestEntidad adaptarTramaBN(String trama) {
        // TODO: LOGICA ADATAR TRAMAS DEL BN A ENTIDAD 
        
        



    }

    @Override
    public ResponseEntidad adaptarTramaEntidad(String trama) {
        // TODO: LOGICA ADATAR TRAMAS DEL ENTIDAD BN 
      throw new UnsupportedOperationException("Unimplemented method 'adaptarTramaEntidad'");
    }

  
}
