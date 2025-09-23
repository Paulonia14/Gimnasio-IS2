package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfesorController {
    @Autowired
    private SocioRepository socioRepository;

    private boolean esProfesor(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.PROFESOR;
    }

    @GetMapping("/profesor/dashboard")
    public String dashboardProfesor(HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        return "views/profesor/dashboard";
    }

    @GetMapping("/profesor/seguimiento")
    public String seguimientoSocios(HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        return "views/profesor/seguimientoSocios";
    }

    @GetMapping("/profesor/cumpleaños")
    public String verCumpleanios(HttpSession session, Model model) {
        if (!esProfesor(session)) return "redirect:/login";

        List<Socio> cumpleanios = socioRepository.findCumpleaniosProximos30Dias();
        model.addAttribute("cumpleanios", cumpleanios);
        model.addAttribute("rol","profesor");
        return "views/cumpleaños";
    }
}
