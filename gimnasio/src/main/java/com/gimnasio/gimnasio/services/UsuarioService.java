package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void crearUsuario(String nombreUsuario, String clave, RolUsuario rol) throws Exception{
        try {
            Usuario user = new Usuario();
            user.setNombreUsuario(nombreUsuario);
            user.setClave(clave);
            user.setRol(rol);
            usuarioRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al crear el usuario: " + e.getMessage());
        }
    }


    @Transactional
    public void modificarUsuario(String idUsuario, String nombreUsuario, String clave, RolUsuario rol) throws Exception{
        try {
            Optional<Usuario> user = usuarioRepository.findById(idUsuario);
            if (user.isPresent()) {
                Usuario userAct = user.get();
                userAct.setNombreUsuario(nombreUsuario);
                userAct.setClave(clave);
                userAct.setRol(rol);
                usuarioRepository.save(userAct);
            } else {
                throw new Exception("Usuario no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar usuario: " + e.getMessage());
        }
    }
    @Transactional
    public void eliminarUsuario(String idUsuario) throws Exception{
        try {
            Usuario user = buscarUsuario(idUsuario);
            user.setEliminado(true);
            usuarioRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al eliminar usuario: " + e.getMessage());
        }
    }

    public Usuario buscarUsuario(String idUsuario) throws Exception{
        try {
            Optional<Usuario> user = usuarioRepository.findById(idUsuario);
            if (user.isPresent()) {
                Usuario userActual = user.get();
                return userActual;
            } else {
                throw new Exception("Usuario no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar usuario: " + e.getMessage());
        }
    }
    @Transactional
    public List<Usuario> listarUsuarios() throws Exception {
        try {
            List<Usuario> entities = this.usuarioRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Usuario> listarUsuariosActivos() throws Exception {
        try {
            return this.usuarioRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
