package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Direccion;
import com.gimnasio.gimnasio.entities.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, String> {

    @Query(value = "SELECT * FROM rutinas WHERE eliminado = false", nativeQuery = true)
    List<Rutina> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM rutinas WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Rutina> findByIdAndEliminadoFalse(@Param("id") String id);

}
