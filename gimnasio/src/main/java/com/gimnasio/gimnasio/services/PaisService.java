package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Pais;
import com.gimnasio.gimnasio.repositories.PaisRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaisService implements ServicioBase<Pais> {

    @Autowired
    private PaisRepository paisRepository;

    @Override
    @Transactional
    public List<Pais> findAll() throws Exception {
        try {
            List<Pais> entities = this.paisRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Pais findById(String id) throws Exception {
        try {
            Optional<Pais> opt = this.paisRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Pais saveOne(Pais entity) throws Exception {
        try {
            Pais pais = this.paisRepository.save(entity);
            return pais;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Pais updateOne(Pais entity, String id) throws Exception {
        try {
            Optional<Pais> opt = this.paisRepository.findById(id);
            Pais pais = opt.get();
            pais.setNombre(entity.getNombre());
            pais = this.paisRepository.save(pais);
            return pais;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Pais> opt = this.paisRepository.findById(id);
            if (opt.isPresent()) {
                Pais pais = opt.get();
                pais.setEliminado(!pais.getEliminado());
                this.paisRepository.save(pais);
            } else {
                throw new Exception("Pais no encontrado");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*   Métodos nuevos   */

    @Transactional
    public List<Pais> findAllByEliminadoFalse() throws Exception {
        try {
            List<Pais> entities = this.paisRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Pais findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Pais> opt = this.paisRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}