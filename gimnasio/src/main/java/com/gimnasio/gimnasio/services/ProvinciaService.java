package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Provincia;
import com.gimnasio.gimnasio.repositories.PaisRepository;
import com.gimnasio.gimnasio.repositories.ProvinciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinciaService implements ServicioBase<Provincia> {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Override
    @Transactional
    public List<Provincia> findAll() throws Exception {
        try {
            List<Provincia> entities = this.provinciaRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Provincia findById(String id) throws Exception {
        try {
            Optional<Provincia> opt = this.provinciaRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Provincia saveOne(Provincia entity) throws Exception {
        try {
            Provincia provincia = this.provinciaRepository.save(entity);
            return provincia;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Provincia updateOne(Provincia entity, String id) throws Exception {
        try {
            Optional<Provincia> opt = this.provinciaRepository.findById(id);
            Provincia provincia = opt.get();
            provincia.setNombre(entity.getNombre());
            provincia.setPais(entity.getPais());
            provincia = this.provinciaRepository.save(provincia);
            return provincia;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Provincia> opt = this.provinciaRepository.findById(id);
            if (opt.isPresent()) {
                Provincia provincia = opt.get();
                provincia.setEliminado(!provincia.getEliminado());
                this.provinciaRepository.save(provincia);
            } else {
                throw new Exception("Provincia no encontrada");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*   Métodos nuevos   */

    @Transactional
    public List<Provincia> findAllByEliminadoFalse() throws Exception {
        try {
            List<Provincia> entities = this.provinciaRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Provincia findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Provincia> opt = this.provinciaRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}