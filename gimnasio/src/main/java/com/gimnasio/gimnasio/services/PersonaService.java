package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Persona;
import com.gimnasio.gimnasio.repositories.PersonaRepository;
import com.gimnasio.gimnasio.services.ServicioBase;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService implements ServicioBase<Persona> {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    @Transactional
    public List<Persona> findAll() throws Exception {
        try {
            List<Persona> entities = this.personaRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Persona findById(String id) throws Exception {
        try {
            Optional<Persona> opt = this.personaRepository.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Persona> opt = this.personaRepository.findById(id);
            if (opt.isPresent()) {
                Persona persona = opt.get();
                persona.setEliminado(!persona.getEliminado());
                this.personaRepository.save(persona);
            } else {
                throw new Exception("Persona no encontrada");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*   Métodos nuevos   */

    @Transactional
    public List<Persona> findAllByEliminadoFalse() throws Exception {
        try {
            List<Persona> entities = this.personaRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Persona findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Persona> opt = this.personaRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}