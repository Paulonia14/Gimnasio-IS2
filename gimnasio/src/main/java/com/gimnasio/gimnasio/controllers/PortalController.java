package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String inicio() {
        try {
            return "views/inicio"; // <-- coincide con tu carpeta templates/views/
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        try {
            return "views/login"; // <-- apunta a templates/views/login.html
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                Model model) {

        // ⚡ Ejemplo básico de validación
        if (email.equals("admin@sportgym.com") && password.equals("1234")) {
            return "redirect:/inicio";
        } else {
            model.addAttribute("error", "Credenciales inválidas. Intente nuevamente.");
            return "views/login";
        }
    }
}
