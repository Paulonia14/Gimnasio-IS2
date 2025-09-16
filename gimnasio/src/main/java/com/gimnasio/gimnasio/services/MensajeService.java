package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Mensaje;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.enumerations.TipoMensaje;
import com.gimnasio.gimnasio.repositories.MensajeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    public void crearMensaje(String titulo, String texto, TipoMensaje tipoMensaje) throws Exception{
        try {
            validar(titulo, texto, tipoMensaje);
            Mensaje mensaje = new Mensaje();
            mensaje.setTitulo(titulo);
            mensaje.setTexto(texto);
            mensaje.getTipoMensaje();
            mensaje.setEliminado(false);
            mensajeRepository.save(mensaje);
        } catch (Exception e) {
            throw new Exception("Error al crear mensaje: " + e.getMessage());
        }
    }

    public void validar (String titulo, String texto, TipoMensaje tipoMensaje) throws Exception{
        if(titulo == null || titulo.trim().isEmpty()){
            throw new Exception("El título no puede estar vacío");
        }
        if(texto == null || texto.trim().isEmpty()){
            throw new Exception("El texto del mensaje no puede estar vacío");
        }
        if(tipoMensaje == null){
            throw new Exception("El tipo de mensaje es obligatorio");
        }
    }

    public void modificarMensaje(String idUsuario, String titulo, String texto, TipoMensaje tipoMensaje) throws Exception{
        try {
            validar(titulo, texto, tipoMensaje);
            Optional<Mensaje> mensaje = mensajeRepository.findById(idUsuario);
            if (mensaje.isPresent()) {
                Mensaje mensajeActual = mensaje.get();
                mensajeActual.setTitulo(titulo);
                mensajeActual.setTexto(texto);
                mensajeActual.setTipoMensaje(tipoMensaje);
                mensajeRepository.save(mensajeActual);
            } else {
                throw new Exception("Mensaje no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar mensaje: " + e.getMessage());
        }
    }

    public void eliminarMensaje(String idUsuario) throws Exception{
        try {
            Mensaje mensaje = buscarMensaje(idUsuario);
            mensaje.setEliminado(true);
            mensajeRepository.save(mensaje);
        } catch (Exception e) {
            throw new Exception("Error al eliminar mensaje: " + e.getMessage());
        }
    }

    public Mensaje buscarMensaje(String idUsuario) throws Exception{
        try {
            Optional<Mensaje> mensaje = mensajeRepository.findById(idUsuario);
            if (mensaje.isPresent()) {
                Mensaje mensajeActual = mensaje.get();
                return mensajeActual;
            } else {
                throw new Exception("Mensaje no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar mensaje: " + e.getMessage());
        }
    }

    @Transactional
    public List<Mensaje> listarMensajes() throws Exception {
        try {
            List<Mensaje> entities = this.mensajeRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Mensaje> listarMensajesActivos() throws Exception {
        try {
            return this.mensajeRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Falta enviarMensaje

}
