package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuotaMensualRepository extends JpaRepository<CuotaMensual, String> {
    // Lista los mensajes activos
    @Query(value = "SELECT * FROM CuotaMensual WHERE eliminado = false", nativeQuery = true)
    List<CuotaMensual> findAllByEliminadoFalse();
    // Antes tenía FROM cuotas_mensuales que es una native query
    // Encuentra un mensaje activo por id
    @Query(value = "SELECT * FROM CuotaMensual WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<CuotaMensual> findByIdAndEliminadoFalse(@Param("id") String id);

    // Para listar por Estado de la cuota
    @Query("SELECT c FROM CuotaMensual c WHERE c.estado = :estado AND c.eliminado = false")
    List<CuotaMensual> findByEstadoAndEliminadoFalse(@Param("estado") EstadoCuotaMensual estado);

    // Listar por fecha de vencimiento
    @Query("SELECT c FROM CuotaMensual c WHERE c.fechaVencimiento BETWEEN :fechaDesde AND :fechaHasta AND c.eliminado = false")
    List<CuotaMensual> findByFechaVencimientoAndEliminadoFalse(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);

    // Por socio
    List<CuotaMensual> findBySocioNumeroSocioAndEliminadoFalse(Long numeroSocio);
}
