package com.wgeneric.microservices.services;

import org.springframework.stereotype.Service;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.services.imp.JsonTramaAdapter;
import com.wgeneric.microservices.services.interfaces.TramaAdapter;

@Service
public class TramaService {

    public RequestEntidad procesarTramaEnviarEntidad(String trama, String formato) {
        TramaAdapter adapter = null;
        if (formato.equalsIgnoreCase("JSON")) {
            adapter = new JsonTramaAdapter();
        } else if (formato.equalsIgnoreCase("XML")) {
            // adapter = new XmlTramaAdapter();
        } else if (formato.equalsIgnoreCase("PROPIETARIO")) {
            // adapter = new FormatoPropiertarioTramaAdapter();
        } else {
            adapter = new JsonTramaAdapter();
        }
        // Verificar si se asignó un adaptador válido
        if (adapter != null) {
            return adapter.adaptarTramaBN(trama);
        } else {
            throw new IllegalArgumentException("No se pudo encontrar un adaptador para el formato especificado");
        }
    }

    public ResponseEntidad procesarTramaEnviarHost(String trama, String formato) {
        TramaAdapter adapter = null;
        if (formato.equalsIgnoreCase("JSON")) {
            adapter = new JsonTramaAdapter();
        } else if (formato.equalsIgnoreCase("XML")) {
            // adapter = new XmlTramaAdapter();
        } else if (formato.equalsIgnoreCase("PROPIETARIO")) {
            // adapter = new FormatoPropiertarioTramaAdapter();
        } else {
            adapter = new JsonTramaAdapter();
        }
        // Verificar si se asignó un adaptador válido
        if (adapter != null) {
            return adapter.adaptarTramaEntidad(trama);
        } else {
            throw new IllegalArgumentException("No se pudo encontrar un adaptador para el formato especificado");
        }
    }
}
