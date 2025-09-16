package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Persona;
import com.gimnasio.gimnasio.repositories.PersonaRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaService{

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public void eliminarPersona(String id) throws Exception{
        try {
            Persona persona = buscarPersona(id);
            persona.setEliminado(true);
            personaRepository.save(persona);
        } catch (Exception e) {
            throw new Exception("Error al eliminar persona: " + e.getMessage());
        }
    }

    public Persona buscarPersona(String id) throws Exception{
        try {
            Optional<Persona> persona = personaRepository.findById(id);
            if (persona.isPresent()) {
                Persona personaActual = persona.get();
                return personaActual;
            } else {
                throw new Exception("Persona no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar persona: " + e.getMessage());
        }
    }
}