package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.enumerations.Meses;
import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
import com.gimnasio.gimnasio.repositories.CuotaMensualRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CuotaMensualService {
    @Autowired
    private CuotaMensualRepository cuotaMensualRepository;

    public void crearCuotaMensual(Meses mes, long anio, EstadoCuotaMensual estado, Date fechaVencimiento) throws Exception{
        try {
            validar(mes, anio, estado, fechaVencimiento);
            CuotaMensual cuota = new CuotaMensual();
            cuota.setMes(mes);
            cuota.setAnio(anio);
            cuota.setEstado(estado);
            cuota.setFechaVencimiento(fechaVencimiento);
            cuota.setEliminado(false);
            cuotaMensualRepository.save(cuota);
        } catch (Exception e) {
            throw new Exception("Error al crear cuota: " + e.getMessage());
        }
    }

    public void validar (Meses mes, long anio, EstadoCuotaMensual estado, Date fechaVencimiento) throws Exception{
        if(mes == null){
            throw new Exception("El tipo de mensaje es obligatorio");
        }
        if(anio <= 2000){
            throw new Exception("El año debe ser mayor o igual a 2000");
        }
        if(estado == null){
            throw new Exception("El estado es obligatorio");
        }
        if(fechaVencimiento == null){
            throw new Exception("La fecha de vencimiento es obligatoria");
        }
    }

    public void modificarCuotaMensual(String id, Meses mes, long anio, EstadoCuotaMensual estado, Date fechaVencimiento) throws Exception{
        try {
            validar(mes, anio, estado, fechaVencimiento);
            Optional<CuotaMensual> cuotaMensual = cuotaMensualRepository.findById(id);
            if (cuotaMensual.isPresent()) {
                CuotaMensual cuotaActual = cuotaMensual.get();
                cuotaActual.setMes(mes);
                cuotaActual.setAnio(anio);
                cuotaActual.setEstado(estado);
                cuotaActual.setFechaVencimiento(fechaVencimiento);
                cuotaMensualRepository.save(cuotaActual);
            } else {
                throw new Exception("Cuota no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar cuota: " + e.getMessage());
        }
    }

    public void eliminarCuotaMensual(String id) throws Exception{
        try {
            CuotaMensual cuota = buscarCuotaMensual(id);
            cuota.setEliminado(true);
            cuotaMensualRepository.save(cuota);
        } catch (Exception e) {
            throw new Exception("Error al eliminar cuota: " + e.getMessage());
        }
    }

    public CuotaMensual buscarCuotaMensual(String id) throws Exception{
        try {
            Optional<CuotaMensual> cuota = cuotaMensualRepository.findById(id);
            if (cuota.isPresent()) {
                CuotaMensual cuotaActual = cuota.get();
                return cuotaActual;
            } else {
                throw new Exception("Cuota no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar cuota: " + e.getMessage());
        }
    }

    @Transactional
    public List<CuotaMensual> listarCuotas() throws Exception {
        try {
            List<CuotaMensual> entities = this.cuotaMensualRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<CuotaMensual> listarCuotasActivas() throws Exception {
        try {
            return this.cuotaMensualRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<CuotaMensual> listarCuotaMensualPorEstado(EstadoCuotaMensual estado) throws Exception {
        try {
            return this.cuotaMensualRepository.findByEstadoAndEliminadoFalse(estado);
        } catch (Exception e) {
            throw new Exception("Error al listar cuotas por estado: " + e.getMessage());
        }
    }

    // Desde el controlador se transforma mes y anio en fechaDesde
    @Transactional
    public List<CuotaMensual> listarCuotaMensualPorFecha(Date fechaDesde, Date fechaHasta) throws Exception {
        try {
            if (fechaDesde == null || fechaHasta == null) {
                throw new Exception("Las fechas no pueden ser nulas");
            }
            if (fechaDesde.after(fechaHasta)) {
                throw new Exception("La fecha desde no puede ser después de la fecha hasta");
            }
            return this.cuotaMensualRepository.findByFechaVencimientoAndEliminadoFalse(fechaDesde, fechaHasta);
        } catch (Exception e) {
            throw new Exception("Error al listar cuotas por fecha: " + e.getMessage());
        }
    }

}
