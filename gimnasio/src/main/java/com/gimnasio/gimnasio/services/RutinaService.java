package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Rutina;
import com.gimnasio.gimnasio.repositories.RutinaRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RutinaService implements ServicioBase<Rutina> {

    @Autowired
    private RutinaRepository rutinaRepository;

    @Override
    @Transactional
    public List<Rutina> findAll() throws Exception {
        try {
            List<Rutina> entities = this.rutinaRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Rutina findById(String id) throws Exception {
        try {
            Optional<Rutina> opt = this.rutinaRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Rutina saveOne(Rutina entity) throws Exception {
        try {
            Rutina rutina = this.rutinaRepository.save(entity);
            return rutina;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Rutina updateOne(Rutina entity, String id) throws Exception {
        try {
            Optional<Rutina> opt = this.rutinaRepository.findById(id);
            Rutina rutina = opt.get();
            rutina.setFechaInicio(entity.getFechaInicio());
            rutina.setFechaFinalizacion(entity.getFechaFinalizacion());
            rutina.setEstadoRutina(entity.getEstadoRutina());
            rutina.setEmpleado(entity.getEmpleado());
            rutina.setSocio(entity.getSocio());
            rutina = this.rutinaRepository.save(rutina);
            return rutina;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Rutina> opt = this.rutinaRepository.findById(id);
            if (opt.isPresent()) {
                Rutina rutina = opt.get();
                rutina.setEliminado(!rutina.getEliminado());
                this.rutinaRepository.save(rutina);
            } else {
                throw new Exception("Rutina no encontrada");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Rutina> findAllByEliminadoFalse() throws Exception {
        try {
            List<Rutina> entities = this.rutinaRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Rutina findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Rutina> opt = this.rutinaRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}