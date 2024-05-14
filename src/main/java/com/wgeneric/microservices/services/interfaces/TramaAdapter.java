package com.wgeneric.microservices.services.interfaces;

import com.wgeneric.microservices.models.comunicacion.RequestEntidad;
import com.wgeneric.microservices.models.comunicacion.ResponseEntidad;

public interface TramaAdapter {

   public  RequestEntidad adaptarTramaBN(String trama);
   public  ResponseEntidad adaptarTramaEntidad(String trama);

}
