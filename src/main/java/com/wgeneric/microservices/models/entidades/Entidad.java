package com.wgeneric.microservices.models.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wgeneric.microservices.models.entidades.enums.ServiceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "f01_entidad")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Entidad {
    

     @Id
     @Column(name = "f01_entidadId", nullable = false, unique = true) // postgresql
     @NotNull
     @Size(min = 4, max = 4, message = "El ID debe tener exactamente 4 dígitos")
     private String id;


    @Column(name = "f01_nombre",nullable = false)
    private String nombre;
    
    
    @Column(name = "f01_descripcion",nullable = false)
    private String descripcion;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "f01_tipo",nullable = false)
    private ServiceType type;

    @Column(name = "f01_estado")
    private int estado;
    /**
     * ESTADO
     * 1 = ACTIVO
     * 0 = DESATIVADO
     */

    @OneToMany(mappedBy = "entidad")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
    private List<Interfaces> interfaces;
  









}
