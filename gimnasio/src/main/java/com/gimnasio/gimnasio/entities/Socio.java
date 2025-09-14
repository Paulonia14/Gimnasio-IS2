package com.gimnasio.gimnasio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "socios")
public class Socio extends Persona {
    @NotNull(message = "El número de socio es obligatorio")
    @Min(value = 1, message = "El número de socio debe ser mayor a 0")
    private Long numeroSocio;

}
