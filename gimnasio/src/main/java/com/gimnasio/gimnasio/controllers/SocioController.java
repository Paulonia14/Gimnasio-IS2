package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SocioController {

    private boolean esSocio(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.SOCIO;
    }

    @GetMapping("/socio/dashboard")
    public String dashboardSocio(HttpSession session) {
        if (!esSocio(session)) return "redirect:/login";
        return "views/socio/dashboard";
    }

    @GetMapping("/socio/rutinas")
    public String misRutinas(HttpSession session) {
        if (!esSocio(session)) return "redirect:/login";
        return "views/socio/rutinas";
    }

    @GetMapping("/socio/cuotas")
    public String misCuotas(HttpSession session) {
        if (!esSocio(session)) return "redirect:/login";
        return "views/socio/cuotas";
    }

    @GetMapping("/socio/deuda")
    public String miDeuda(HttpSession session) {
        if (!esSocio(session)) return "redirect:/login";
        return "views/socio/deuda";
    }

    @GetMapping("/socio/cumpleaños")
    public String cumpleanos(HttpSession session) {
        if (!esSocio(session)) return "redirect:/login";
        return "views/socio/cumpleaños";
    }
}
