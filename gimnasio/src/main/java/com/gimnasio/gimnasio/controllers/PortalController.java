package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalController {


    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String inicio(){
    try{
        return "views/inicio";
    } catch (Exception e) {
//        model.addAttribute("error", e.getMessage());
        return "error";
    }

    }

}
