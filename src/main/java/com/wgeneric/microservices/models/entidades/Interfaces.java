package com.wgeneric.microservices.models.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wgeneric.microservices.models.entidades.enums.MethodType;
import com.wgeneric.microservices.models.entidades.enums.OperationType;

import jakarta.persistence.*;
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
@Table(name = "f02_interfaces")
public class Interfaces {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "f02_interfazId",nullable = false)
	    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // El id se incluye en las respuestas, no en las solicitudes
	    private Integer id;

		// Este campo es para recibir el ID de la entidad en el JSON
		@Column(name = "f01_entidadId", insertable = false, updatable = false)
		private String entidadId;

	    @ManyToOne
	    @JoinColumn(name = "f01_entidadId")
	    @JsonIgnore
		@ToString.Exclude
		private Entidad entidad;


	    @Enumerated(EnumType.STRING)
	    @Column(name = "f02_operacion",nullable = false)
	    private OperationType operationType;


	    @Enumerated(EnumType.STRING)
	    @Column(name = "f02_metodo",nullable = false)
	    private MethodType methodType;

	     @Column(name = "f02_endpoint",nullable = false)
	     //@Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "La URL debe ser válida y comenzar con http o https")
	    private String endpoint;





		@Column(name = "f02_timeout",nullable = false)
		//@Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "La URL debe ser válida y comenzar con http o https")
	   private Integer timeout;





	    @OneToMany(mappedBy = "interfaces", fetch = FetchType.EAGER)
	    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
	    private List<Plantilla> plantillas;
	    
	    
	    
	    @OneToMany(mappedBy = "interfaces")
	    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
	    private List<Parametros> parametros;
	    
	    
	    
	    
	    
	    
}