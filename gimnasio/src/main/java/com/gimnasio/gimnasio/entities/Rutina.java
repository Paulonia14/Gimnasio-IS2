package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.EstadoRutina;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rutinas")
public class Rutina {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotNull(message = "La fecha  obligatoria")
    @Past(message = "La fecha debe ser en el pasado")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @NotNull(message = "La fecha  obligatoria")
    @Past(message = "La fecha debe ser en el pasado")
    @Temporal(TemporalType.DATE)
    private Date fechaFinalizacion;

    @NotNull(message = "El campo es requerido")
    private EstadoRutina estadoRutina;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private Boolean eliminado = false;

    @NotNull(message = "El campo es requerido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_socio", nullable = false)
    private Socio socio;

    @NotNull(message = "El campo es requerido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_empleado", nullable = false)
    private Empleado empleado;

    @NotNull(message = "El campo es requerido")
    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<DetalleRutina> detalleRutinas;

}
