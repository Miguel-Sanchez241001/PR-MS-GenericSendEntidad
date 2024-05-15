package com.wgeneric.microservices.models.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "f05_caracteristicas")
public class Caracteristicas {

 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f05_caracteristicasId",nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // El id se incluye en las respuestas, no en las solicitudes
    private Integer id;

// Este campo es para recibir el ID de la entidad en el JSON
@Column(name = "f05_caracteristica_name"  )
private String caracteristica;
	
}
