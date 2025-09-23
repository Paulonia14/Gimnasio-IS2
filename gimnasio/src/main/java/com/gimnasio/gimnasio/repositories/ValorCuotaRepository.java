package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.ValorCuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValorCuotaRepository extends JpaRepository<ValorCuota, String> {
    List<ValorCuota> findByEliminadoFalse();
}
