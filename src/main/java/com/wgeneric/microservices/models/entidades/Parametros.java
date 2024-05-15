package com.wgeneric.microservices.models.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "f06_parametros")
public class Parametros {
 

 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f06_parametrosId",nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // El id se incluye en las respuestas, no en las solicitudes
    private Integer id;
 	
 	
 	
 	
 	
    // Este campo es para recibir el ID de la entidad en el JSON
    @Column(name = "f02_interfazId", insertable = false, updatable = false)
    private Integer interfazId;
    
    
    @ManyToOne
    @JoinColumn(name = "f02_interfazId")
    @JsonIgnore
    private Interfaces interfaces;
    
    
    
    
    
    // Este campo es para recibir el ID de la entidad en el JSON
    @Column(name = "f05_caracteristicasId", insertable = false, updatable = false)
    private Integer caracteristicaId;
    
    @ManyToOne
    @JoinColumn(name = "f05_caracteristicasId")
    @JsonIgnore
    private Caracteristicas caracteristicas;
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	  @Column(name = "f06_parametro",nullable = false)
 	    private String parametro;
 	  

 	  @Column(name = "f06_valor",nullable = false)
 	    private String valor;
 	
 	
 	
 	
 	
 	
}
