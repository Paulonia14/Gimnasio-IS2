package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.services.CuotaMensualService;
import com.gimnasio.gimnasio.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SocioController {

    @Autowired
    private CuotaMensualService cuotaMensualService;

    @Autowired
    private UsuarioService usuarioService;

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
    public String misCuotas(HttpSession session, Model model) {
        if (!esSocio(session)) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        try {
            Socio socio = usuarioService.getSocio(usuario);
            List<CuotaMensual> cuotas = cuotaMensualService.listarCuotasPorSocio(socio.getNumeroSocio());
            model.addAttribute("cuotas", cuotas);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
