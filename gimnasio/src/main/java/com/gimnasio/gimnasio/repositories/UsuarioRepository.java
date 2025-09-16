package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // Lista los mensajes activos
    @Query(value = "SELECT * FROM usuarios WHERE eliminado = false", nativeQuery = true)
    List<Usuario> findAllByEliminadoFalse();

    // Encuentra un mensaje activo por id
    @Query(value = "SELECT * FROM usuarios WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Usuario> findByIdAndEliminadoFalse(@Param("id") String id);
}
