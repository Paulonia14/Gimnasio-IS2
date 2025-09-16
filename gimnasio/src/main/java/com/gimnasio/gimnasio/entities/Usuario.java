package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private String nombreUsuario;
    private String clave;
    private Roles rol;
    private boolean eliminado;
}
