package com.gimnasio.gimnasio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "promociones")
public class Promocion extends Mensaje{
    private Date fechaEnvioPromocion; // Esta fecha tiene que ser en el futuro (o en el mismo momento tambien?)
    @Min(value = 0, message = "La cantidad de socios enviados no puede ser negativa")
    private long cantidadSociosEnviados;
}
