package com.wgeneric.microservices.services.imp.operaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.models.entidades.Entidad;
import com.wgeneric.microservices.models.entidades.Interfaces;
import com.wgeneric.microservices.models.entidades.Plantilla;
import com.wgeneric.microservices.models.entidades.enums.PlantillaType;
import com.wgeneric.microservices.models.multiservice.RequestMS;
import com.wgeneric.microservices.models.multiservice.ResponseMS;
import com.wgeneric.microservices.repositorios.EntidadRepo;
import com.wgeneric.microservices.services.ComunicacionFacade;
import com.wgeneric.microservices.services.TramaService;
import com.wgeneric.microservices.services.interfaces.Operacion;
import com.wgeneric.microservices.util.Constantes;

import java.util.Objects;

@Service
public class Consulta implements Operacion{

    @Autowired
    private TramaService tramaService;
    @Autowired
    private EntidadRepo entidadRepo;
 


    @Override
    public ResponseMS operacionEntidad(RequestMS requestMS) {
        ResponseMS responseMS  = new ResponseMS();
        ComunicacionFacade comunicacionFacade = new ComunicacionFacade();



    	if (!Objects.equals(requestMS.getCodoper(), Constantes.COG_CONSULTA)) {
            //TODO: OPERACION NO ES CONSULTA RETORNAR 
            
        }else{
            Entidad entidad = entidadRepo.findById(requestMS.getIdentidad()).get();
            Interfaces interfaz =   entidad.getInterfaces().stream().filter(inter -> inter.getOperationType().toString() == requestMS.getCodoper())
                                                                    .findFirst()
                                                                    .orElse(null);

              Plantilla plantilla = interfaz.getPlantillas().stream().filter(planti -> planti.getPlantillaType()==PlantillaType.REQUEST ) 
                                                                        .findFirst()
                                                                        .orElse(null);    
                                                                        
                                                                        
              RequestEntidad requestEntidad = RequestEntidad.builder()
                                                            .entidad(entidad)
                                                            .interfaz(interfaz)
                                                            .plantilla(plantilla)
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
    
}
