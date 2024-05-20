package com.wgeneric.microservices.services.imp;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.models.entidades.CamposTG;
import com.wgeneric.microservices.models.entidades.Plantilla;
import com.wgeneric.microservices.services.interfaces.TramaAdapter;

public class JsonTramaAdapter implements TramaAdapter {

    private RequestEntidad requestEntidad;
    public JsonTramaAdapter(RequestEntidad requestEntidad) {
        this.requestEntidad = requestEntidad;
    }
 

 

    @Override
    public RequestEntidad adaptarTramaBN(Plantilla plantilla, String trama) {
        // TODO: LOGICA PARA ADAPTAR LA TRAMA DEL BN A LA ENTIDAD 
        
        List<CamposTG> campos = plantilla.getCamposTags();
        try {
          // Paso 1: Convertir el array de bytes a una cadena de texto
        byte[] jsonData = plantilla.getContenido(); // Método para obtener el JSON como byte[]
        String jsonString = new String(jsonData, "UTF-8");

        // Paso 2: Convertir la cadena de texto JSON a un objeto JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
 
        for (CamposTG camposTG : campos) {
            String mensaje = trama.substring(camposTG.getPosicion_final(), camposTG.getPosicion_final());
            cambiarValores(rootNode,mensaje,camposTG);
        }




        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            // TODO: CONTROLAR EL ERROR PARA EL PARSEO DEL JSON
            e.printStackTrace();
        }



       











        return requestEntidad;
    }

	    private  void cambiarValores(JsonNode node,String mensaje,CamposTG campo) {
	        if (node.isObject()) {
	            ObjectNode objectNode = (ObjectNode) node;
	            
	            objectNode.fields().forEachRemaining(entry -> {
	                JsonNode valueNode = entry.getValue();
	                if (valueNode.isTextual()) {
	                    String valor = valueNode.textValue();
	                    if (valor.equals(campo.getParam_campo())) {
	                        objectNode.put(entry.getKey(), mensaje); // Cambia aquí por el nuevo valor deseado
	                    }
	                } else {
	                    cambiarValores(valueNode,mensaje,campo);
	                }
	            });
	        } else if (node.isArray()) {
	            node.forEach( item -> cambiarValores(item, mensaje, campo));
	        }
	    }


    @Override
    public ResponseEntidad adaptarTramaEntidad(Plantilla plantilla, String trama) {
        // TODO: LOGICA PARA ADAPTAR LA TRAMA DE LA ENTIDAD AL BN 
        throw new UnsupportedOperationException("Unimplemented method 'adaptarTramaEntidad'");
    }

  
}
