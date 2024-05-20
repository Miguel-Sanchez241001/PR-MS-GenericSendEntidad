package com.wgeneric.microservices.models.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity
@Table(name = "f04_campos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CamposTG {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f04_camposId",nullable = false)
  //  @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // El id se incluye en las respuestas, no en las solicitudes
    private Integer id;
	
	

    @Column(name = "f04_ param_campo",nullable = false)
    private String  param_campo;

    @Column(name = "f04_valor",nullable = false)
    private String valor;

    @Column(name = "f04_posicion_inicial",nullable = false)
    private int posicion_inicial;

    @Column(name = "f04_posicion_final",nullable = false)
    private int posicion_final;

    @Column(name = "f04_dimension",nullable = false)
    private String dimension;

    @Column(name = "f04_tipo_campo",nullable = false)
    private String tipo_campo;

    @Column(name = "f04_valor_defecto",nullable = false)
    private String valor_defecto;
    
    
    
    // Este campo es para recibir el ID de la entidad en el JSON
    @Column(name = "f02_interfazId", insertable = false, updatable = false)
    private Integer plantilaId;
    
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "f03_plantillaId")
    @ToString.Exclude
    private Plantilla plantilla;
}
