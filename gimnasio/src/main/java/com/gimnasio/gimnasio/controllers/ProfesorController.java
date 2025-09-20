package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfesorController {

    private boolean esProfesor(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.PROFESOR;
    }

    @GetMapping("/profesor/dashboard")
    public String dashboardProfesor(HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        return "views/profesor/dashboard";
    }

    @GetMapping("/profesor/rutinas")
    public String gestionRutinas(HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        return "views/profesor/rutinas";
    }

    @GetMapping("/profesor/seguimiento")
    public String seguimientoSocios(HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        return "views/profesor/seguimientoSocios";
    }

    @GetMapping("/profesor/cumpleaños")
    public String cumpleanios(HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        return "views/profesor/cumpleaños";
    }
}
