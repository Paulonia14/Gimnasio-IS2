package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {

    @Query("SELECT s FROM Socio s WHERE s.eliminado = false")
    List<Socio> findAllByEliminadoFalse();

    @Query("SELECT s FROM Socio s WHERE s.id = :id AND s.eliminado = false")
    Optional<Socio> findByIdAndEliminadoFalse(@Param("id") String id);

    boolean existsByNumeroSocio(Long numeroSocio); // Comprobar unicidad del num de socio

    @Query("SELECT MAX(s.numeroSocio) FROM Socio s")
    Long findUltimoNumeroSocio();

    @Query(value = """
    SELECT * 
    FROM socios s
    JOIN personas p ON s.id = p.id
    WHERE p.eliminado = false
      AND (
        DATE_FORMAT(p.fecha_nacimiento, '%m-%d')
        BETWEEN DATE_FORMAT(CURDATE(), '%m-%d')
            AND DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 30 DAY), '%m-%d')
      )
    """, nativeQuery = true)
    List<Socio> findCumpleaniosProximos30Dias();

    // Busca socios que cumplan años para mandar el mail
    @Query("SELECT s FROM Socio s WHERE FUNCTION('DAY', s.fechaNacimiento) = :day AND FUNCTION('MONTH', s.fechaNacimiento) = :month AND s.eliminado = false")
    List<Socio> findSociosCumpleaniosHoy(@Param("day") int day, @Param("month") int month);

}
