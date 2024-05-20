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
            return adapter.adaptarTramaBN(trama);
        } else {
            throw new IllegalArgumentException("No se pudo encontrar un adaptador para el formato especificado");
        }
    }

    public ResponseEntidad procesarTramaEnviarHost(RequestEntidad requestEntidad,String trama)  {
        TramaAdapter adapter = null;
        if (requestEntidad.getPlantilla().getDocType().toString().equalsIgnoreCase("JSON")) {
            adapter = new JsonTramaAdapter(requestEntidad);
        } 
        // Verificar si se asignó un adaptador válido
        if (adapter != null) {
            return adapter.adaptarTramaEntidad(trama);
        } else {
            throw new IllegalArgumentException("No se pudo encontrar un adaptador para el formato especificado");
        }
    }
}
