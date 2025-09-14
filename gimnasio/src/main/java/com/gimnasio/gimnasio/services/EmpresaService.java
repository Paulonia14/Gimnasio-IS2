package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Empresa;
import com.gimnasio.gimnasio.repositories.EmpresaRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements ServicioBase<Empresa> {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    @Transactional
    public List<Empresa> findAll() throws Exception {
        try {
            List<Empresa> entities = this.empresaRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Empresa findById(String id) throws Exception {
        try {
            Optional<Empresa> opt = this.empresaRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Empresa saveOne(Empresa entity) throws Exception {
        try {
            Empresa empresa = this.empresaRepository.save(entity);
            return empresa;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Empresa updateOne(Empresa entity, String id) throws Exception {
        try {
            Optional<Empresa> opt = this.empresaRepository.findById(id);
            Empresa empresa = opt.get();
            empresa.setNombre(entity.getNombre());
            empresa.setTelefono(entity.getTelefono());
            empresa.setCorreoElectronico(entity.getCorreoElectronico());

            empresa = this.empresaRepository.save(empresa);
            return empresa;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Empresa> opt = this.empresaRepository.findById(id);
            if (opt.isPresent()) {
                Empresa empresa = opt.get();
                empresa.setEliminado(!empresa.getEliminado());
                this.empresaRepository.save(empresa);
            } else {
                throw new Exception("Empresa no encontrada");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Empresa> findAllByEliminadoFalse() throws Exception {
        try {
            List<Empresa> entities = this.empresaRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Empresa findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Empresa> opt = this.empresaRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}