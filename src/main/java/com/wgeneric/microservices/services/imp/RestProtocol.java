
package com.wgeneric.microservices.services.imp;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.models.entidades.Caracteristicas;
import com.wgeneric.microservices.models.entidades.Entidad;
import com.wgeneric.microservices.models.entidades.Parametros;
import com.wgeneric.microservices.models.entidades.enums.MethodType;
import com.wgeneric.microservices.models.entidades.enums.ServiceType;
import com.wgeneric.microservices.services.interfaces.CommunicationProtocol;
import com.wgeneric.microservices.util.HttpClientRest;

public class RestProtocol implements CommunicationProtocol {

    @Override
    public ResponseEntidad sendRequest(RequestEntidad requestEntidad) throws IOException, InterruptedException {
        Entidad entidadExterna = requestEntidad.getEntidad();
        ResponseEntidad responseEntidad = new ResponseEntidad();

        if (!entidadExterna.getType().equals(ServiceType.REST)) {
            // NO ES DE TIPO REST ABORTA ENVIO
        } else{
            if (!requestEntidad.getInterfaz().getMethodType().equals(MethodType.OTRO)) {

                Map<String, String> respuestaHttp = null;

                // TODO ARMAR HEADER
                List<Parametros> headers = requestEntidad.getParametros().stream().filter(parametros -> parametros.getCaracteristicas().getCaracteristica().equals("HEADER"))
                        .toList();
                // Convertir la lista de headers a un mapa
                Map<String, String> headerMap = headers.stream()
                        .collect(Collectors.toMap(Parametros::getParametro, Parametros::getValor));
                requestEntidad.setHeaders(headerMap);
                if (requestEntidad.getInterfaz().getMethodType().equals(MethodType.POST)) {
                    respuestaHttp = new HttpClientRest().ClientConnectPost(requestEntidad.getSolicitudEntidadArmada(), requestEntidad.getHeaders(), requestEntidad.getInterfaz());
                }else{
                    respuestaHttp =  new HttpClientRest().ClientConnectGet(requestEntidad.getSolicitudEntidadArmada(), requestEntidad.getHeaders(), requestEntidad.getInterfaz());
                }
                responseEntidad.setHttpCodigo(respuestaHttp.get("codigoRespuesta"));
                responseEntidad.setBody(respuestaHttp.get("body"));
            
            
            }
        }
        
        return responseEntidad;
         


       
    }

   
}
