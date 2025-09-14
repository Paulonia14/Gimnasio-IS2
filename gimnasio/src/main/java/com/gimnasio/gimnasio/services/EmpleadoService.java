package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Empleado;
import com.gimnasio.gimnasio.repositories.EmpleadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService implements ServicioBase<Empleado> {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    @Transactional
    public List<Empleado> findAll() throws Exception {
        try {
            return empleadoRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Empleado findById(String id) throws Exception {
        try {
            Optional<Empleado> opt = empleadoRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Empleado saveOne(Empleado entity) throws Exception {
        try {
            return empleadoRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Empleado updateOne(Empleado entity, String id) throws Exception {
        try {
            Optional<Empleado> opt = empleadoRepository.findById(id);
            Empleado empleado = opt.get();

            empleado.setNombre(entity.getNombre());
            empleado.setApellido(entity.getApellido());
            empleado.setFechaNacimiento(entity.getFechaNacimiento());
            empleado.setTipoDocumento(entity.getTipoDocumento());
            empleado.setNumeroDocumento(entity.getNumeroDocumento());
            empleado.setTelefono(entity.getTelefono());
            empleado.setCorreoElectronico(entity.getCorreoElectronico());
            empleado.setDireccion(entity.getDireccion());
            empleado.setSucursal(entity.getSucursal());
            empleado.setTipoEmpleado(entity.getTipoEmpleado());


            return empleadoRepository.save(empleado);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Empleado> opt = empleadoRepository.findById(id);
            if (opt.isPresent()) {
                Empleado empleado = opt.get();
                empleado.setEliminado(!empleado.getEliminado());
                empleadoRepository.save(empleado);
            } else {
                throw new Exception("Empleado no encontrado");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Empleado> findAllByEliminadoFalse() throws Exception {
        try {
            return empleadoRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Empleado findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Empleado> opt = empleadoRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}