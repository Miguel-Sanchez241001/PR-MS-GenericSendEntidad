package com.wgeneric.microservices.models.comunicacion;

import java.util.List;
import java.util.Map;

import com.wgeneric.microservices.models.entidades.Entidad;
import com.wgeneric.microservices.models.entidades.Interfaces;
import com.wgeneric.microservices.models.entidades.Parametros;
import com.wgeneric.microservices.models.entidades.Plantilla;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class RequestEntidad {
    private Entidad entidad;
    private Interfaces interfaz;
    private Plantilla plantilla;
    private byte[]  solicitudEntidad;
    private String solicitudEntidadArmada;
    private List<Parametros> parametros;
    private Map<String,String> headers;
}
