package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Mensaje;
import com.gimnasio.gimnasio.entities.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, String> {
    // Lista las promociones activas
    @Query(value = "SELECT * FROM promociones WHERE eliminado = false", nativeQuery = true)
    List<Promocion> findAllByEliminadoFalse();

    // Encuentra una promocion activa
    @Query(value = "SELECT * FROM promociones WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Promocion> findByIdAndEliminadoFalse(@Param("id") String id);

    @Query("SELECT p FROM Promocion p " + "WHERE p.fechaEnvioPromocion BETWEEN :inicio AND :fin " + "AND p.eliminado = false")
    List<Promocion> findByFechaEnvioPromocionBetween(@Param("inicio") Date inicio, @Param("fin") Date fin);
}
