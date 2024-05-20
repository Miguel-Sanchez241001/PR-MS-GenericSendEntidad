package com.wgeneric.microservices.services;

import org.springframework.stereotype.Service;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.services.imp.JsonTramaAdapter;
import com.wgeneric.microservices.services.interfaces.TramaAdapter;

@Service
public class TramaService {

    public RequestEntidad procesarTramaEnviarEntidad(RequestEntidad requestEntidad,String trama) {
        TramaAdapter adapter = null;
        if (requestEntidad.getPlantilla().getDocType().toString().equalsIgnoreCase("JSON")) {
            adapter = new JsonTramaAdapter(requestEntidad);
        } 
        // Verificar si se asignó un adaptador válido
        if (adapter != null) {
            return adapter.adaptarTramaBN(requestEntidad.getPlantilla(), trama);
        } else {
            return requestEntidad;
        }
    }

    public ResponseEntidad procesarTramaEnviarHost(RequestEntidad requestEntidad,String trama)  {
        TramaAdapter adapter = null;
        if (requestEntidad.getPlantilla().getDocType().toString().equalsIgnoreCase("JSON")) {
            adapter = new JsonTramaAdapter(requestEntidad);
        } 
        // Verificar si se asignó un adaptador válido
        if (adapter != null) {
            return adapter.adaptarTramaEntidad(requestEntidad.getPlantilla(),trama);
        } else {
            return new ResponseEntidad();
        }
    }
}
