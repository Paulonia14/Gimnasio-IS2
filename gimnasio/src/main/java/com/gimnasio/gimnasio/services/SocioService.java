package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.enumerations.TipoDocumento;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;

    public Long obtenerUltimoNumeroSocio() {
        Long ultimo = socioRepository.findUltimoNumeroSocio();
        return (ultimo == null) ? 0L : ultimo;
    }

    @Transactional
    public void crearSocio(String nombre, String apellido, Date fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico) throws Exception{
        try {
            validar(nombre, apellido, fechaNacimiento, tipoDocumento, numeroDocumento, telefono, correoElectronico );
            Socio socio = new Socio();
            socio.setNombre(nombre);
            socio.setApellido(apellido);
            socio.setFechaNacimiento(fechaNacimiento);
            socio.setTipoDocumento(tipoDocumento);
            socio.setNumeroDocumento(numeroDocumento);
            socio.setTelefono(telefono);
            socio.setCorreoElectronico(correoElectronico);
            Long ultimo = obtenerUltimoNumeroSocio();
            socio.setNumeroSocio(ultimo + 1);
            socio.setEliminado(false);
            socioRepository.save(socio);
        } catch (Exception e) {
            throw new Exception("Error al crear socio: " + e.getMessage());
        }
    }

    public void modificarSocio(String nombre, String apellido, Date fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico, Long numeroSocio) throws Exception{
        try {
            Optional<Socio> optSocio = socioRepository.findById(numeroSocio);
            if (optSocio.isPresent()) {
                Socio socio = optSocio.get();
                validar(nombre, apellido, fechaNacimiento, tipoDocumento, numeroDocumento, telefono, correoElectronico);
                socio.setNombre(nombre);
                socio.setApellido(apellido);
                socio.setFechaNacimiento(fechaNacimiento);
                socio.setTipoDocumento(tipoDocumento);
                socio.setNumeroDocumento(numeroDocumento);
                socio.setTelefono(telefono);
                socio.setCorreoElectronico(correoElectronico);
                socioRepository.save(socio);
            } else {
                throw new Exception("Socio no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar socio: " + e.getMessage());
        }
    }


    @Transactional
    public boolean deleteById(Long numSocio) throws Exception {
        try {
            Optional<Socio> opt = this.socioRepository.findById(numSocio);
            if (opt.isPresent()) {
                Socio socio = opt.get();
                socio.setEliminado(true);
                this.socioRepository.save(socio);
            } else {
                throw new Exception("Socio no encontrado");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Transactional
    public List<Socio> findAll() throws Exception {
        try {
            List<Socio> entities = this.socioRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Socio findById(Long numSocio) throws Exception {
        try {
            Optional<Socio> opt = this.socioRepository.findById(numSocio);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Socio saveOne(Socio entity) throws Exception {
        try {
            Socio socio = this.socioRepository.save(entity);
            return socio;
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

    // Hay que validar tambien num socio
    public void validar(String nombre, String apellido, Date fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("El apellido no puede estar vacío.");
        }
        if (fechaNacimiento == null) {
            throw new Exception("La fecha de nacimiento es obligatoria.");
        }
        if (tipoDocumento == null) {
            throw new Exception("El tipo de documento es obligatorio.");
        }
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new Exception("El DNI no puede estar vacío.");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new Exception("El teléfono no puede estar vacío.");
        }
        if (correoElectronico == null || correoElectronico.trim().isEmpty()) {
            throw new Exception("El email no puede estar vacío.");
        }
        if (!correoElectronico.contains("@")) {
            throw new Exception("El email no tiene un formato válido.");
        }
    }


}


// falta asociarSocioUsuario