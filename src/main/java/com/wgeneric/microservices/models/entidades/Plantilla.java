package com.wgeneric.microservices.models.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wgeneric.microservices.models.entidades.enums.DocType;
import com.wgeneric.microservices.models.entidades.enums.PlantillaType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
@Table(name = "f03_plantilla")
public class Plantilla {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "f03_plantillaId",nullable = false)
	    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // El id se incluye en las respuestas, no en las solicitudes
	    private Integer id;

	 	
	 	
	 	
	 	
	    @JsonIgnore
	    @ManyToOne
	    @JoinColumn(name = "f02_interfazId")
	    @ToString.Exclude
	    private Interfaces interfaces;
	    
	    
	    
	    
	    
	    
	    // Este campo es para recibir el ID de la entidad en el JSON
	    @Column(name = "f02_interfazId", insertable = false, updatable = false)
	    private Integer interfazId;
	   
	    @Column(name = "f03_descripcion",nullable = false)
	    private String descripcion;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "f03_tipo",nullable = false)
	    private PlantillaType plantillaType;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "f03_doctipo",nullable = false)
	    private DocType docType;

	    @Column(name = "f03_plantilla",nullable = false)
	    @Lob
	    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // El id se incluye en las respuestas, no en las solicitudes
	    @ToString.Exclude
	    private byte[] contenido;

	    @Transient
	    @ToString.Exclude
	    private String contenidoSTR;

	    @OneToMany(mappedBy = "plantilla")
	    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
	    @ToString.Exclude
	    private List<CamposTG> camposTags;
}
