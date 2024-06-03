package com.wgeneric.microservices.services.interfaces;

import com.wgeneric.microservices.models.entidades.CamposTG;
import com.wgeneric.microservices.models.entidades.Plantilla;

import java.util.List;

public interface CamposInt {
    public List<CamposTG> salvarCamposValue(Plantilla plantilla);

    List<CamposTG> salvarCamposKey(Plantilla plantilla);
}
