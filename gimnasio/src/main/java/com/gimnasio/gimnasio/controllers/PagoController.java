package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.services.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
public class PagoController {

    private final PagoService testMP;

    public PagoController(PagoService testMP) {
        this.testMP = testMP;
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
}
