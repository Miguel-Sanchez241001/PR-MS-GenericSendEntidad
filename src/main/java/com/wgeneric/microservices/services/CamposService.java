package com.wgeneric.microservices.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgeneric.microservices.models.entidades.CamposTG;
import com.wgeneric.microservices.models.entidades.Caracteristicas;
import com.wgeneric.microservices.models.entidades.Parametros;
import com.wgeneric.microservices.models.entidades.Plantilla;
import com.wgeneric.microservices.models.entidades.enums.PlantillaType;
import com.wgeneric.microservices.repositorios.CamposTGRepo;
import com.wgeneric.microservices.repositorios.CaracteristicasRepo;
import com.wgeneric.microservices.repositorios.ParametroRepo;
import com.wgeneric.microservices.services.interfaces.CamposInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CamposService implements CamposInt {
    @Autowired
    public CamposTGRepo camposTGRepo;
    @Autowired
    public ParametroRepo parametroRepo;
    @Autowired
    public CaracteristicasRepo caracteristicasRepo;
    @Override
    public List<CamposTG> salvarCamposValue(Plantilla plantilla) {
        List<CamposTG> lista = new ArrayList<>();

        // Convertir el byte[] a una cadena de texto JSON
        String jsonString = new String(plantilla.getContenido());

        // Inicializar el ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Leer el JSON como un árbol de nodos
            JsonNode rootNode = objectMapper.readTree(jsonString);




            // Llamar a la función recursiva para procesar el JSON
            lista = procesarJSON(rootNode,plantilla);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<CamposTG> salvarCamposKey(Plantilla plantilla) {
        List<CamposTG>    lista = null;
        // Convertir el byte[] a una cadena de texto JSON
        String jsonString = new String(plantilla.getContenido());

        // Inicializar el ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Leer el JSON como un árbol de nodos
            JsonNode rootNode = objectMapper.readTree(jsonString);



            // Llamar a la función recursiva para procesar el JSON
            lista = procesarJSON(rootNode,plantilla);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    private List<CamposTG> procesarJSON(JsonNode rootNode,Plantilla plantilla) {
        List<CamposTG> prmDataList = new ArrayList<>();
        if (rootNode.isObject()) {
            // Iterar sobre todas las claves y valores del JSON
            rootNode.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode valueNode = entry.getValue();

                // Imprimir la clave
                System.out.println("Clave: " + key);

                // Si el valor empieza con "prm", capturarlo
                if (valueNode.isTextual() && valueNode.textValue().toUpperCase().startsWith("PRM")) {
                    String valor = valueNode.textValue();
                    String[] listadatosValor = valor.split("_");

                    String TipCampo = plantilla.getPlantillaType().equals(PlantillaType.REQUEST) ? "VALOR" : "KEY";
                    String paramCampo = plantilla.getPlantillaType().equals(PlantillaType.REQUEST) ? valor : key;
                    CamposTG camposTG = CamposTG.builder()
                            .plantilla(plantilla)
                            .plantilaId(plantilla.getId())
                            .param_campo(paramCampo)
                            .tipo_campo(TipCampo)
                            .tipo_valor(listadatosValor[1])
                            .dimension("9")
                            .posicion_final(0)
                            .posicion_final(10)
                            .build();

                    prmDataList.add(camposTGRepo.save(camposTG));
                    if (paramCampo.startsWith("PRM_D_")) {
                        Parametros parametros = new Parametros();
                        parametros.setInterfazId(plantilla.getInterfazId());
                        parametros.setInterfaces(plantilla.getInterfaces());
                     Caracteristicas caracteristicas =  caracteristicasRepo.findById(2).get();
                        parametros.setCaracteristicas(caracteristicas);
                        parametros.setCaracteristicaId(caracteristicas.getId());
                        parametros.setParametro(paramCampo);
                        parametros.setValor(paramCampo);
                        parametroRepo.save(parametros);
                     }
                    System.out.println(TipCampo + " que empieza con prm: " + valor);
                }

                // Si el valor es un objeto, llamar recursivamente a la función
                if (valueNode.isObject()) {
                    prmDataList.addAll(procesarJSON(valueNode, plantilla));
                }

            });

        }
        return prmDataList;
    }
}
