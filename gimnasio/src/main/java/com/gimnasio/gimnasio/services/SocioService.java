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
public class SocioService {
    @Autowired
    private SocioRepository socioRepository;

    public void crearSocio(String nombre, String apellido, Date fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico, Long numeroSocio) {
        try {
            //validar(nombre, apellido, fechaNacimiento, tipoDocumento, numeroDocumento, telefono, correoElectronico, numeroSocio);
            Socio socio = new Socio();
            socio.setNombre(nombre);
            socio.setApellido(apellido);
            socio.setFechaNacimiento(fechaNacimiento);
            socio.setTipoDocumento(tipoDocumento);
            socio.setNumeroDocumento(numeroDocumento);
            socio.setTelefono(telefono);
            socio.setCorreoElectronico(correoElectronico);
            socio.setNumeroSocio(numeroSocio);
            socio.setEliminado(false);
            socioRepository.save(socio);
        } catch (DataAccessException ex) {
            System.err.println("Error al crear socio -> " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.err.println("Validación fallida al crear socio -> " + ex.getMessage());
        }
    }

    public void validar(String nombre, String apellido, Date fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico, Long numeroSocio) {

    }

    public void modificarSocio(String id, String nombre, String apellido, Date fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, Long numeroSocio){
        try {
            Optional<Socio> opcionalSocio = socioRepository.findById(id);
            if (opcionalSocio.isPresent()) {
                Socio socio = opcionalSocio.get();
                socio.setNombre(nombre);
                socio.setApellido(apellido);
                socio.setFechaNacimiento(fechaNacimiento);
                socio.setTipoDocumento(tipoDocumento);
                socio.setNumeroDocumento(numeroDocumento);
                socio.setNumeroSocio(numeroSocio);
                socioRepository.save(socio);

            } else{
                System.out.println("No existe el socio con el id: " + id);
            }
        } catch (DataAccessException ex){
            System.err.println("Error al modificar socio -> " + ex.getMessage());
        }
    }

    public Collection<Socio> listarSocio() {
        try {
            return socioRepository.findAll();
        } catch (DataAccessException ex) {
            System.err.println("Error al listar socios -> " + ex.getMessage());
            return List.of();
        }
    }

    @Transactional
    public Collection<Socio> listarSocioActivo() {
        try {
            return socioRepository.findAllActivos();
        } catch (DataAccessException ex) {
            System.err.println("Error al listar socios activos -> " + ex.getMessage());
            return List.of();
        }
    }

    // falta validar y asociarSocioUsuario
}
