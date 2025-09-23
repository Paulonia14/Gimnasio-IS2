package com.gimnasio.gimnasio.controllers.admin;

import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
import com.gimnasio.gimnasio.services.CuotaMensualService;
import com.gimnasio.gimnasio.services.SocioService;
import com.gimnasio.gimnasio.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AdminDeudaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SocioService socioService;

    @Autowired
    private CuotaMensualService cuotaMensualService;

    public String verDeudas(Model model) {
        List<Socio> socios;
        try {
            // obtenemos todos los socios activos
            socios = socioService.findAllByEliminadoFalse();
        } catch (Exception e) {
            socios = new ArrayList<>();
            System.out.println("Error al listar socios: " + e.getMessage());
        }

        List<Map<String, Object>> sociosConDeuda = new ArrayList<>();

        for (Socio socio : socios) {
            List<CuotaMensual> cuotas;
            try {
                cuotas = cuotaMensualService.listarCuotasPorSocio(socio.getNumeroSocio());
            } catch (Exception e) {
                cuotas = new ArrayList<>();
                System.out.println("Error al obtener cuotas del socio " + socio.getNumeroSocio());
            }

            int mesesDeuda = 0;
            double totalDeuda = 0.0;

            for (CuotaMensual cuota : cuotas) {
                if (cuota.getEstado() == EstadoCuotaMensual.ADEUDADA) {
                    mesesDeuda++;
                    totalDeuda += cuota.getValorCuota().getValorCuota();
                }
            }

            if (mesesDeuda > 0) {
                Map<String, Object> datos = new HashMap<>();
                datos.put("socio", socio);
                datos.put("mesesDeuda", mesesDeuda);
                datos.put("totalDeuda", totalDeuda);
                sociosConDeuda.add(datos);
            }
        }

        model.addAttribute("sociosConDeuda", sociosConDeuda);

        if (sociosConDeuda.isEmpty()) {
            model.addAttribute("mensaje", "No hay socios con deuda.");
        }

        return "views/admin/deudas";
    }

}
