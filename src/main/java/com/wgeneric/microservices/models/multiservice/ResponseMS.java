package com.wgeneric.microservices.models.multiservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMS {
    
	private String codigoRetorno;
	private String desMensaje;
	private String tramaRespuesta;
}
