package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.EstadoFactura;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private Long numeroFactura;
    private Date fechaFactura;
    private double totalPagado;
    @NotNull(message = "El estado de la factura es obligatorio")
    @Enumerated(EnumType.STRING)
    private EstadoFactura estadoFactura;
    @NotNull(message = "El campo eliminado no puede ser nulo")
    private boolean eliminado;
}
