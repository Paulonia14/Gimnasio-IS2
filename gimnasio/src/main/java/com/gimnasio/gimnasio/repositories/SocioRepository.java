package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SocioRepository extends JpaRepository<Socio, String> {
    @Query(value = "SELECT * FROM socios WHERE socios.id = :id AND socios.eliminado = false", nativeQuery = true)
    Optional<Socio> findByIdAndNoEliminado(@Param("id") String id);

    //Todos los socios activos
    @Query(value = "SELECT * FROM socios WHERE socios.eliminado = false", nativeQuery = true)
    List<Socio> findAllActivos();
}
