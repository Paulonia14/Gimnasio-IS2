package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.enumerations.TipoDocumento;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SocioService implements ServicioBase<Socio> {

    @Autowired
    private SocioRepository socioRepository;

    @Override
    @Transactional
    public List<Socio> findAll() throws Exception {
        try {
            List<Socio> entities = this.socioRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Socio findById(String id) throws Exception {
        try {
            Optional<Socio> opt = this.socioRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Socio saveOne(Socio entity) throws Exception {
        try {
            Socio socio = this.socioRepository.save(entity);
            return socio;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Socio updateOne(Socio entity, String id) throws Exception {
        try {
            Optional<Socio> opt = this.socioRepository.findById(id);
            Socio socio = opt.get();

            socio.setNombre(entity.getNombre());
            socio.setApellido(entity.getApellido());
            socio.setFechaNacimiento(entity.getFechaNacimiento());
            socio.setTipoDocumento(entity.getTipoDocumento());
            socio.setNumeroDocumento(entity.getNumeroDocumento());
            socio.setTelefono(entity.getTelefono());
            socio.setCorreoElectronico(entity.getCorreoElectronico());
            socio.setNumeroSocio(entity.getNumeroSocio());
            socio.setDireccion(entity.getDireccion());
            socio.setSucursal(entity.getSucursal());

            socio = this.socioRepository.save(socio);
            return socio;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Socio> opt = this.socioRepository.findById(id);
            if (opt.isPresent()) {
                Socio socio = opt.get();
                socio.setEliminado(!socio.getEliminado());
                this.socioRepository.save(socio);
            } else {
                throw new Exception("Socio no encontrado");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*   Métodos nuevos   */

    @Transactional
    public List<Socio> findAllByEliminadoFalse() throws Exception {
        try {
            List<Socio> entities = this.socioRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Socio findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Socio> opt = this.socioRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}


// falta validar y asociarSocioUsuario