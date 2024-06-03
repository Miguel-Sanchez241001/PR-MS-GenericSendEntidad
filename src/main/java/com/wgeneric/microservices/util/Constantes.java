package com.wgeneric.microservices.util;

import com.wgeneric.microservices.models.entidades.enums.OperationType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constantes {
    


        // CODIGO DE ERROR 
        public final static String COG_EXITO = "0000";
        
        // OPERACIONES
        
        public final static String COG_CONSULTA = "0001";
        public final static String COG_PAGO = "0002";
        public final static String COG_EXTORNO = "0003";

        public static final Map<String, OperationType> DICTIONARY;

        static {
                Map<String, OperationType> map = new HashMap<>();
                map.put("0001", OperationType.CONSULTA);
                map.put("0002", OperationType.PAGO);
                map.put("0003", OperationType.EXTORNO);
                DICTIONARY = Collections.unmodifiableMap(map);
        }
}
