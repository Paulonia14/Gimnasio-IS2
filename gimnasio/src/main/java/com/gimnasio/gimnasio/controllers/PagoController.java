package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
import com.gimnasio.gimnasio.services.CuotaMensualService;
import com.gimnasio.gimnasio.services.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
public class PagoController {

    private final PagoService testMP;
    private final CuotaMensualService cuotaService;

    public PagoController(PagoService testMP, CuotaMensualService cuotaService) {
        this.testMP = testMP;
        this.cuotaService = cuotaService;
    }

    @GetMapping("/pago")
    public String pagar() throws IOException {
        try {
            //  arma la preference y nos da la URL de pago
            String initPoint = testMP.Pago();

            // caso de exito Redirigimos al usuario a Mercado Pago
            return "redirect:" + initPoint;


        } catch (Exception e) {
            return "redirect:/inicio";
        }
    }
    @GetMapping("/socio/pagar-cuota/{id}")
    public String pagarCuota(@PathVariable String id) throws Exception {
        CuotaMensual cuota = cuotaService.buscarCuotaMensual(id);

        // Cambiar el estado a PAGADA
        cuotaService.modificarCuotaMensual(id, cuota.getSocio().getId(), cuota.getMes(), cuota.getAnio(), EstadoCuotaMensual.PAGADA, cuota.getFechaVencimiento());

        return "redirect:/pago";
    }

}
