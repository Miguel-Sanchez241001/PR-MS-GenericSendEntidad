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
public class RequestMS {
	
    private String identidad ;
    private String codoper ;
    private String body ;
    
}
