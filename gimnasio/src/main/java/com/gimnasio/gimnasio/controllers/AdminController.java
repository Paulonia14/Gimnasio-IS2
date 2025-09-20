package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    // Método auxiliar para validar si el usuario es admin
    private boolean esAdmin(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.ADMINISTRATIVO;
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        return "views/admin/dashboard";
    }

    @GetMapping("/admin/socios")
    public String gestionSocios(HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        return "views/admin/socios";
    }

    @GetMapping("/admin/usuarios")
    public String gestionUsuarios(HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        return "views/admin/usuarios";
    }

    @GetMapping("/admin/cuotas")
    public String gestionCuotas(HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        return "views/admin/cuotas";
    }

    @GetMapping("/admin/deudas")
    public String gestionDeudas(HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        return "views/admin/deudas";
    }

    @GetMapping("/admin/campañas")
    public String gestionCampanias(HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        return "views/admin/campañas";
    }

    @GetMapping("/admin/cumpleaños")
    public String gestionCumpleanios(HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        return "views/admin/cumpleaños";
    }

}
