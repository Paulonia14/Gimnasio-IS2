package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Localidad;
import com.gimnasio.gimnasio.repositories.LocalidadRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LocalidadService implements ServicioBase<Localidad> {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Override
    @Transactional
    public List<Localidad> findAll() throws Exception {
        try {
            List<Localidad> entities = this.localidadRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Localidad findById(String id) throws Exception {
        try {
            Optional<Localidad> opt = this.localidadRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Localidad saveOne(Localidad entity) throws Exception {
        try {
            Localidad localidad = this.localidadRepository.save(entity);
            return localidad;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Localidad updateOne(Localidad entity, String id) throws Exception {
        try {
            Optional<Localidad> opt = this.localidadRepository.findById(id);
            Localidad localidad = opt.get();
            localidad.setNombre(entity.getNombre());
            localidad.setCodigoPostal(entity.getCodigoPostal());
            localidad.setDepartamento(entity.getDepartamento());
            localidad = this.localidadRepository.save(localidad);
            return localidad;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Localidad> opt = this.localidadRepository.findById(id);
            if (opt.isPresent()) {
                Localidad localidad = opt.get();
                localidad.setEliminado(!localidad.getEliminado());
                this.localidadRepository.save(localidad);
            } else {
                throw new Exception("Localidad no encontrada");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*   Métodos nuevos   */

    @Transactional
    public List<Localidad> findAllByEliminadoFalse() throws Exception {
        try {
            List<Localidad> entities = this.localidadRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Localidad findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Localidad> opt = this.localidadRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}