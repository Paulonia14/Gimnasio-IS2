package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Sucursal;
import com.gimnasio.gimnasio.repositories.SucursalRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SucursalService implements ServicioBase<Sucursal> {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    @Transactional
    public List<Sucursal> findAll() throws Exception {
        try {
            List<Sucursal> entities = this.sucursalRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Sucursal findById(String id) throws Exception {
        try {
            Optional<Sucursal> opt = this.sucursalRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Sucursal saveOne(Sucursal entity) throws Exception {
        try {
            Sucursal sucursal = this.sucursalRepository.save(entity);
            return sucursal;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Sucursal updateOne(Sucursal entity, String id) throws Exception {
        try {
            Optional<Sucursal> opt = this.sucursalRepository.findById(id);
            Sucursal sucursal = opt.get();
            sucursal.setNombre(entity.getNombre());
            sucursal.setDireccion(entity.getDireccion());
            sucursal.setEmpresa(entity.getEmpresa());
            sucursal = this.sucursalRepository.save(sucursal);
            return sucursal;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Sucursal> opt = this.sucursalRepository.findById(id);
            if (opt.isPresent()) {
                Sucursal sucursal = opt.get();
                sucursal.setEliminado(!sucursal.getEliminado());
                this.sucursalRepository.save(sucursal);
            } else {
                throw new Exception("Sucursal no encontrada");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*   Métodos nuevos   */

    @Transactional
    public List<Sucursal> findAllByEliminadoFalse() throws Exception {
        try {
            List<Sucursal> entities = this.sucursalRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Sucursal findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Sucursal> opt = this.sucursalRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}