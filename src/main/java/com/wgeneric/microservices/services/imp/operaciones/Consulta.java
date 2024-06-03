package com.wgeneric.microservices.services.imp.operaciones;

import com.wgeneric.microservices.models.entidades.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.models.entidades.enums.PlantillaType;
import com.wgeneric.microservices.models.multiservice.RequestMS;
import com.wgeneric.microservices.models.multiservice.ResponseMS;
import com.wgeneric.microservices.repositorios.EntidadRepo;
import com.wgeneric.microservices.services.ComunicacionFacade;
import com.wgeneric.microservices.services.TramaService;
import com.wgeneric.microservices.services.interfaces.Operacion;
import com.wgeneric.microservices.util.Constantes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class Consulta implements Operacion{

    @Autowired
    public TramaService tramaService;
    @Autowired
    public EntidadRepo entidadRepo;

    private static final Logger logger = LoggerFactory.getLogger(Consulta.class);


    @Override
    public ResponseMS operacionEntidad(RequestMS requestMS) throws IOException, InterruptedException {
        ResponseMS responseMS  = new ResponseMS();
        ComunicacionFacade comunicacionFacade = new ComunicacionFacade();



    	if (!Objects.equals(requestMS.getCodoper(), Constantes.COG_CONSULTA)) {
            //TODO: OPERACION NO ES CONSULTA COMTROLAR ERROR
            
        }else{

            requestMS.setCodoper(Constantes.DICTIONARY.get(Constantes.COG_CONSULTA).toString());


            Entidad entidad = entidadRepo.findById(requestMS.getIdentidad()).get();
            Interfaces interfaz =   entidad.getInterfaces().stream().filter(inter -> inter.getOperationType().toString() == requestMS.getCodoper())
                                                                    .findFirst()
                                                                    .orElse(null);
            List<Parametros> parametros = interfaz.getParametros();
              Plantilla plantilla = interfaz.getPlantillas().stream().filter(planti -> planti.getPlantillaType()==PlantillaType.REQUEST ) 
                                                                        .findFirst()
                                                                        .orElse(null);

           String url = verificarUrl(interfaz,plantilla,requestMS.getBody());
           interfaz.setEndpoint(url);



              RequestEntidad requestEntidad = RequestEntidad.builder()
                                                            .entidad(entidad)
                                                            .interfaz(interfaz)
                                                            .plantilla(plantilla)
                                                            .parametros(parametros)
                                                            .build();
              
            

            requestEntidad = tramaService.procesarTramaEnviarEntidad(requestEntidad,requestMS.getBody());

            comunicacionFacade.setComunicacionFacade(entidad.getType().toString());

            ResponseEntidad respuestasEntidad = comunicacionFacade.sendRequest(requestEntidad);

            ResponseEntidad responseEntidad = tramaService.procesarTramaEnviarHost(requestEntidad,respuestasEntidad.getBody());

            if (responseEntidad.getHttpCodigo()!= "200") {
                // error solicitud 
            }else{
                responseMS.setCodigoRetorno("0000"); 
                responseMS.setDesMensaje ("EXITO EN INVOCACION DE SERVICIO EXTERNO"); 
                responseMS.setTramaRespuesta(responseEntidad.getBody());
            }
        }
        return responseMS;
    	 
    }

    private String verificarUrl(Interfaces interfaz,Plantilla plantilla, String body) {
        String URL = interfaz.getEndpoint();
        for (Parametros parametro : interfaz.getParametros()) {
            if (parametro.getCaracteristicas().getCaracteristica().equals("URL")){
                for (CamposTG camposTG: plantilla.getCamposTags()){
                    if (camposTG.getParam_campo().equals(parametro.getParametro())){
                        String valorUrl = body.substring(camposTG.getPosicion_final(), camposTG.getPosicion_final());
                        URL = URL.replace(camposTG.getParam_campo(),valorUrl);
                    }
                }
            }
        }
    return URL;
    }



}
