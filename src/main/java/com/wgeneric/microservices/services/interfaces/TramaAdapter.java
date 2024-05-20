package com.wgeneric.microservices.services.interfaces;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;
import com.wgeneric.microservices.models.entidades.Plantilla;

public interface TramaAdapter {

   public  RequestEntidad adaptarTramaBN(Plantilla plantilla , String trama);
   public  ResponseEntidad adaptarTramaEntidad(Plantilla plantilla , String trama);

}
