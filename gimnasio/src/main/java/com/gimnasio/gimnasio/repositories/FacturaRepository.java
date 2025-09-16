package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, String> {
}
