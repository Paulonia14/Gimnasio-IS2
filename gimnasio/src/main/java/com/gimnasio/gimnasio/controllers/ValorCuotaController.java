package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.ValorCuota;
import com.gimnasio.gimnasio.services.ValorCuotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ValorCuotaController {

    @Autowired
    private ValorCuotaService valorCuotaService;


    @GetMapping("/admin/cuotas")
    public String historialCuotas(Model model) {
        List<ValorCuota> historial = valorCuotaService.listarTodos();
        model.addAttribute("historialCuotas", historial);
        return "views/admin/cuotas";
    }

    @GetMapping("/admin/cuotas-cambiar")
    public String mostrarFormularioCambio(Model model) {
        model.addAttribute("nuevoValor", new ValorCuota());
        return "views/admin/cuotas-cambiar";
    }

    @PostMapping("/admin/cuotas-cambiar")
    public String cambiarValorCuota(@RequestParam("valorCuota") double valor, Model model) {
        try {
            valorCuotaService.cambiarValorCuota(valor);
            return "redirect:/admin/cuotas";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/admin/cuotas-cambiar";
        }
    }
}
