package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Persona;
import com.gimnasio.gimnasio.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    // Buscar persona por ID
    public Persona buscarPersona(String id) {
        try {
            Optional<Persona> persona = personaRepository.findById(id);
            return persona.orElse(null);
        } catch (DataAccessException ex) {
            System.err.println("Error al buscar persona con ID: " + id + " -> " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Error inesperado al buscar persona: " + ex.getMessage());
            return null;
        }
    }

    // Eliminado logico de unna persona
    public void eliminarPersona(String id) {
        try {
            Optional<Persona> persona = personaRepository.findById(id);
            if (persona.isPresent()) {
                Persona p = persona.get();
                p.setEliminado(true);
                personaRepository.save(p);
            } else {
                System.out.println("Persona con ID " + id + " no encontrada.");
            }
        } catch (DataAccessException ex) {
            System.err.println("Error al eliminar persona con ID: " + id + " -> " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error inesperado al eliminar persona: " + ex.getMessage());
        }
    }

}
