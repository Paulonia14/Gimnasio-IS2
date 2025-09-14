package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Empleado;
import com.gimnasio.gimnasio.entities.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    @Query("SELECT s FROM Empleado s WHERE s.eliminado = false")
    List<Empleado> findAllByEliminadoFalse();

    @Query("SELECT s FROM Empleado s WHERE s.id = :id AND s.eliminado = false")
    Optional<Empleado> findByIdAndEliminadoFalse(@Param("id") String id);
}

