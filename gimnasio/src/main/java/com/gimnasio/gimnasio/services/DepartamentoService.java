package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Departamento;
import com.gimnasio.gimnasio.repositories.DepartamentoRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService implements ServicioBase<Departamento> {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Override
    @Transactional
    public List<Departamento> findAll() throws Exception {
        try {
            List<Departamento> entities = this.departamentoRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Departamento findById(String id) throws Exception {
        try {
            Optional<Departamento> opt = this.departamentoRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Departamento saveOne(Departamento entity) throws Exception {
        try {
            Departamento departamento = this.departamentoRepository.save(entity);
            return departamento;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Departamento updateOne(Departamento entity, String id) throws Exception {
        try {
            Optional<Departamento> opt = this.departamentoRepository.findById(id);
            Departamento departamento = opt.get();
            departamento.setNombre(entity.getNombre());
            departamento.setProvincia(entity.getProvincia());
            departamento = this.departamentoRepository.save(departamento);
            return departamento;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Departamento> opt = this.departamentoRepository.findById(id);
            if (opt.isPresent()) {
                Departamento departamento = opt.get();
                departamento.setEliminado(!departamento.getEliminado());
                this.departamentoRepository.save(departamento);
            } else {
                throw new Exception("Departamento no encontrado");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*   Métodos nuevos   */

    @Transactional
    public List<Departamento> findAllByEliminadoFalse() throws Exception {
        try {
            List<Departamento> entities = this.departamentoRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Departamento findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Departamento> opt = this.departamentoRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}