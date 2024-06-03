package com.wgeneric.microservices.services.interfaces;

import com.wgeneric.microservices.models.multiservice.RequestMS;
import com.wgeneric.microservices.models.multiservice.ResponseMS;

import java.io.IOException;

public interface Operacion {
    public ResponseMS operacionEntidad(RequestMS requestMS) throws IOException, InterruptedException;
}
