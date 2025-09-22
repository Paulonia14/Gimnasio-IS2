package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.DetalleRutina;
import com.gimnasio.gimnasio.entities.Rutina;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.services.RutinaService;
import com.gimnasio.gimnasio.services.SocioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/profesor/rutinas")
public class RutinaController {

    @Autowired
    private RutinaService rutinaService;

    @Autowired
    private SocioService socioService;

    private boolean esProfesor(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.PROFESOR;
    }

    @GetMapping
    public String listarRutinas(Model model, HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        try {
            List<Rutina> rutinas = rutinaService.listarRutinaActivo();
            model.addAttribute("rutinas", rutinas);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "views/profesor/rutinas";
    }


    @GetMapping("/crear")
    public String crearRutinaForm(Model model, HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        try {
            List<Socio> socios = socioService.findAllByEliminadoFalse();
            model.addAttribute("socios", socios);
            model.addAttribute("rutina", new Rutina());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "views/profesor/rutinas/crear";
    }

    @PostMapping("/crear")
    public String crearRutina(@RequestParam String idSocio,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFinalizacion,
                              @RequestParam List<String> actividades,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttrs) {
        if (!esProfesor(session)) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        try {
            List<DetalleRutina> detalles = new ArrayList<>();
            for (String act : actividades) {
                detalles.add(rutinaService.crearDetalleRutina(new Date(), act));
            }
            rutinaService.crearRutina(usuario.getId(), idSocio, fechaInicio, fechaFinalizacion, detalles);
            redirectAttrs.addFlashAttribute("success", "✅ Rutina creada con éxito");
            return "redirect:/profesor/rutinas";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            try {
                model.addAttribute("socios", socioService.findAllByEliminadoFalse());
            } catch (Exception ignored) {}
            return "views/profesor/rutinas/crear";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarRutinaForm(@PathVariable String id, Model model, HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        try {
            Rutina rutina = rutinaService.buscarRutina(id);
            List<Socio> socios = socioService.findAllByEliminadoFalse();
            model.addAttribute("rutina", rutina);
            model.addAttribute("socios", socios);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "views/profesor/rutinas/editar";
    }

    @PostMapping("/editar/{id}")
    public String editarRutina(@PathVariable String id,
                               @RequestParam String idSocio,
                               @RequestParam Date fechaInicio,
                               @RequestParam Date fechaFinalizacion,
                               @RequestParam List<String> actividades,
                               HttpSession session,
                               Model model) {
        if (!esProfesor(session)) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        try {
            List<DetalleRutina> detalles = new ArrayList<>();
            for (String act : actividades) {
                detalles.add(rutinaService.crearDetalleRutina(new Date(), act));
            }
            rutinaService.modificarRutina(id, usuario.getId(), idSocio, fechaInicio, fechaFinalizacion, detalles);
            return "redirect:/profesor/rutinas";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            try {
                model.addAttribute("socios", socioService.findAllByEliminadoFalse());
            } catch (Exception ignored) {}
            return "views/profesor/rutinas/editar";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRutina(@PathVariable String id, HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        try {
            rutinaService.eliminarRutina(id);
        } catch (Exception ignored) {}
        return "redirect:/profesor/rutinas";
    }
}
