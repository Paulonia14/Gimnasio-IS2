package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Mensaje;
import com.gimnasio.gimnasio.entities.Promocion;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.TipoMensaje;
import com.gimnasio.gimnasio.repositories.PromocionRepository;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private final SocioRepository socioRepository;
    private final UsuarioRepository usuarioRepository;
    private final MensajeService mensajeService;
    private final PromocionRepository promocionRepository;

    public NotificacionService(SocioRepository socioRepository, UsuarioRepository usuarioRepository, MensajeService mensajeService, PromocionRepository promocionRepository) {
        this.socioRepository = socioRepository;
        this.usuarioRepository = usuarioRepository;
        this.mensajeService = mensajeService;
        this.promocionRepository = promocionRepository;
    }

    // ---- Cumpleaños ----
    public void enviarFelicitacion() {
        // Mensaje de cumpleaños a los socios que cumplan en ese mismo dia
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        int day = hoy.getDayOfMonth();
        int month = hoy.getMonthValue();
        List<Socio> cumpleanieros = socioRepository.findSociosCumpleaniosHoy(day, month);

        for (Socio socio : cumpleanieros) {
            String asunto = "🎉🥳 ¡Feliz cumpleaños " + socio.getNombre() + "!🎉🥳";
            String cuerpo = "¡¡¡Hola " + socio.getNombre() + "!!!,\n\n" + "El equipo de Sport Gym te desea un muy feliz cumpleaños 🥳💯💥😁😎.\n" + "🔥🤑¡Que tengas día increíble!🤑🔥\n\n" + "Atentamente: Sport Gym.";

            //emailService.enviarEmail(socio.getCorreoElectronico(), asunto, cuerpo);
            try {
                Optional<Usuario> usuario = usuarioRepository.findByPersonaId(socio.getId());
                if (usuario.isPresent()) {
                    Usuario user = usuario.get();
                    Mensaje mensaje = mensajeService.crearMensajeObj(user.getId(), asunto, cuerpo, TipoMensaje.CUMPLEAÑOS);
                    mensajeService.enviarMensaje(mensaje.getId());
                }
            } catch (Exception e) {
                System.err.println("Error al registrar mensaje de cumpleaños: " + e.getMessage());
            }
        }

    }

    @Scheduled(cron = "0 30 17 * * *", zone = "America/Argentina/Buenos_Aires")
    public void tareaDiariaCumpleanios() {
        enviarFelicitacion();
    }


    // ---- Promociones ----

    public void enviarPromocion(String promo) {
        String asunto = "🔥¡Nueva promoción en Sport Gym!🔥";
        String cuerpo = "¡¡¡Hola!!!,\n\n" + "Te informamos que tenemos una nueva promoción:\n\n" + promo + "\n\n" + "Vení y aprovecha esta increíble PROMO.\n\n" + "Atentamente: Sport Gym.";

        List<Socio> socios = socioRepository.findAllByEliminadoFalse();

        for (Socio socio : socios) {
            try {
                Optional<Usuario> usuario = usuarioRepository.findByPersonaId(socio.getId());
                if (usuario.isPresent()) {
                    Usuario user = usuario.get();
                    Mensaje mensaje = mensajeService.crearMensajeObj(user.getId(), asunto, cuerpo, TipoMensaje.PROMOCION);
                    mensajeService.enviarMensaje(mensaje.getId());
                }
            } catch (Exception e) {
                System.err.println("Error al enviar mensaje de promoción: " + e.getMessage());
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "America/Argentina/Buenos_Aires")
    public void enviarPromocionesProgramadas() {
        LocalDate hoy = LocalDate.now();
        Date inicio = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(hoy.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
        List<Promocion> promociones = promocionRepository.findByFechaEnvioPromocionBetween(inicio, fin);
        for (Promocion promo : promociones) {
            try {
                mensajeService.enviarMensaje(promo.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}
