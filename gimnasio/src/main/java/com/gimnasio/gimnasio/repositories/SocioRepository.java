package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
@Repository
public interface SocioRepository extends JpaRepository<Socio, String> {

    @Query("SELECT s FROM Socio s WHERE s.eliminado = false")
    List<Socio> findAllByEliminadoFalse();

    @Query("SELECT s FROM Socio s WHERE s.id = :id AND s.eliminado = false")
    Optional<Socio> findByIdAndEliminadoFalse(@Param("id") String id);
}
