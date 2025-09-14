package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Direccion;
import com.gimnasio.gimnasio.repositories.DireccionRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DireccionService implements ServicioBase<Direccion> {

    @Autowired
    private DireccionRepository direccionRepository;

    @Override
    @Transactional
    public List<Direccion> findAll() throws Exception {
        try {
            List<Direccion> entities = this.direccionRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Direccion findById(String id) throws Exception {
        try {
            Optional<Direccion> opt = this.direccionRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Direccion saveOne(Direccion entity) throws Exception {
        try {
            Direccion direccion = this.direccionRepository.save(entity);
            return direccion;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Direccion updateOne(Direccion entity, String id) throws Exception {
        try {
            Optional<Direccion> opt = this.direccionRepository.findById(id);
            Direccion direccion = opt.get();

            direccion.setCalle(entity.getCalle());
            direccion.setNumeracion(entity.getNumeracion());
            direccion.setBarrio(entity.getBarrio());
            direccion.setManzanaPiso(entity.getManzanaPiso());
            direccion.setCasaDepartamento(entity.getCasaDepartamento());
            direccion.setReferencia(entity.getReferencia());
            direccion.setLocalidad(entity.getLocalidad());

            direccion = this.direccionRepository.save(direccion);
            return direccion;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Direccion> opt = this.direccionRepository.findById(id);
            if (opt.isPresent()) {
                Direccion direccion = opt.get();
                direccion.setEliminado(!direccion.getEliminado());
                this.direccionRepository.save(direccion);
            } else {
                throw new Exception("Dirección no encontrada");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*   Métodos nuevos   */

    @Transactional
    public List<Direccion> findAllByEliminadoFalse() throws Exception {
        try {
            List<Direccion> entities = this.direccionRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Direccion findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Direccion> opt = this.direccionRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}