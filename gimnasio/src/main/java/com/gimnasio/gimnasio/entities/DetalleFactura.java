package com.gimnasio.gimnasio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class DetalleFactura {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    @NotNull(message = "El campo eliminado no puede ser nulo")
    private boolean eliminado;
}
