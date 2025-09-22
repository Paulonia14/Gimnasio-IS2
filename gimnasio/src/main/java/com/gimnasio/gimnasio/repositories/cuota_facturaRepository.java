package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.entities.cuota_factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface cuota_facturaRepository extends JpaRepository<cuota_factura, String> {
    @Query(value = "SELECT id_factura FROM cuota_factura WHERE id_cuota = :id", nativeQuery = true)
    String findFacturaIdByCuotaId(@Param("id") String id);
}
