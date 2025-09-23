package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.entities.Promocion;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.enumerations.TipoMensaje;
import com.gimnasio.gimnasio.repositories.PromocionRepository;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import com.gimnasio.gimnasio.services.NotificacionService;
import com.gimnasio.gimnasio.services.PromocionService;
import com.gimnasio.gimnasio.services.SocioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private SocioService socioService;
    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private PromocionService promocionService;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private boolean esAdmin(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.ADMINISTRATIVO;
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        return "views/admin/dashboard";
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

    @GetMapping("/admin/promociones")
    public String gestionCampanias(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";
        try {
            List<Promocion> promociones = promocionService.listarMensajesActivos();
            model.addAttribute("promociones", promociones);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "views/admin/promociones";
    }

    @GetMapping("/admin/promociones/nuevo")
    public String nuevaPromocion(HttpSession session, Model model) throws Exception {
        if (!esAdmin(session)) return "redirect:/login";

        Promocion promocion = new Promocion();
        // setear automáticamente cantidad de socios activos
        long cantidadActivos = socioService.findAllByEliminadoFalse().size();
        promocion.setCantidadSociosEnviados(cantidadActivos);
        // tipo de mensaje fijo como PROMOCION
        promocion.setTipoMensaje(TipoMensaje.PROMOCION);

        model.addAttribute("promocion", promocion);
        return "views/admin/promocionesForm";
    }

    @PostMapping("/admin/promociones/nuevo")
    public String guardarPromocion(@ModelAttribute Promocion promocion, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        try {

            // BUSCAR EL USUARIO ADMIN
            Usuario admin = usuarioRepository.findFirstByRolAndEliminadoFalse(RolUsuario.ADMINISTRATIVO)
                    .orElseThrow(() -> new Exception("No se encontró usuario admin"));

            // CONTAR SOCIOS ACTIVOS
            long cantidadSocios = socioRepository.contarSociosActivos();
            String titulo = "PROMO";
            promocionService.crearPromocion(admin.getId(), titulo, promocion.getTexto(), promocion.getFechaEnvioPromocion(), cantidadSocios);
            LocalDate fechaHoy = LocalDate.now();
            if (promocion.getFechaEnvioPromocion().equals(fechaHoy)) {
                notificacionService.enviarPromocion(promocion.getTexto());
            }
            return "redirect:/admin/promociones";
        } catch (Exception e) {
            e.printStackTrace();
            return "views/admin/promocionesForm";
        }
    }

    @PostMapping("/admin/promociones/editar/{id}")
    public String editarPromocion(@PathVariable String id, @ModelAttribute Promocion promocion, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        // BUSCAR EL USUARIO ADMIN
        Usuario admin = usuarioRepository.findFirstByRolAndEliminadoFalse(RolUsuario.ADMINISTRATIVO)
                .orElseThrow(() -> new RuntimeException("No se encontró usuario admin"));

        // CONTAR SOCIOS ACTIVOS
        long cantidadSocios = socioRepository.contarSociosActivos();

        String titulo = "PROMO";
        try {
            promocionService.modificarPromocion(id, admin.getId(), titulo, promocion.getTexto(), promocion.getFechaEnvioPromocion(), cantidadSocios);
            return "redirect:/admin/promociones";
        } catch (Exception e) {
            e.printStackTrace();
            return "views/admin/promocionesForm";
        }
    }

    @GetMapping("/admin/promociones/editar/{id}")
    public String editarPromocionForm(@PathVariable String id, HttpSession session, Model model) throws Exception {
        if (!esAdmin(session)) return "redirect:/login";

        Promocion promocion = promocionService.buscarPromocion(id);
        model.addAttribute("promocion", promocion);
        return "views/admin/promocionesForm";
    }



    @GetMapping("/admin/cumpleaños")
    public String verCumpleanios(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";

        List<Socio> cumpleanios = socioRepository.findCumpleaniosProximos30Dias();
        model.addAttribute("cumpleanios", cumpleanios);
        return "views/admin/cumpleaños";
    }
}
