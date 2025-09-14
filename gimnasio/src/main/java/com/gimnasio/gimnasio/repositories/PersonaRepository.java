package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {
    @Query(value = "SELECT * FROM personas WHERE personas.id = :id AND personas.eliminado = false", nativeQuery = true)
    Optional<Persona> findByIdAndNoEliminado(@Param("id") String id);
}