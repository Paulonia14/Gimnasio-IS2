package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Factura;
import com.gimnasio.gimnasio.enumerations.EstadoFactura;
import com.gimnasio.gimnasio.repositories.FacturaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    public void crearFactura(Long numeroFactura, Date fechaFactura, double totalPagado, EstadoFactura estado) throws Exception{
        try {
            validar(numeroFactura, fechaFactura, totalPagado, estado);
            Factura factura = new Factura();
            factura.setNumeroFactura(numeroFactura); // Ver bien lo de numero factura !!!
            factura.setFechaFactura(fechaFactura);
            factura.setTotalPagado(totalPagado);
            factura.setEstadoFactura(estado);
            facturaRepository.save(factura);
        } catch (Exception e) {
            throw new Exception("Error al crear factura: " + e.getMessage());
        }
    }

    public void validar(Long numeroFactura, Date fechaFactura, double totalPagado, EstadoFactura estado) throws Exception{

    }

    public void modificarFactura(String id, Long numeroFactura, Date fechaFactura, double totalPagado, EstadoFactura estado) throws Exception{
        try {
            validar(numeroFactura, fechaFactura, totalPagado, estado);
            Optional<Factura> factura = facturaRepository.findById(id);
            if (factura.isPresent()) {
                Factura facturaActual = factura.get();

            } else {
                throw new Exception("Factura no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar factura: " + e.getMessage());
        }
    }

    public void eliminarFactura(String id) throws Exception{
        try {
            Factura factura = buscarFactura(id);
            factura.setEliminado(true);
            facturaRepository.save(factura);
        } catch (Exception e) {
            throw new Exception("Error al eliminar factura: " + e.getMessage());
        }
    }

    public Factura buscarFactura(String id) throws Exception{
        try {
            Optional<Factura> factura = facturaRepository.findById(id);
            if (factura.isPresent()) {
                Factura facturaActual = factura.get();
                return facturaActual;
            } else {
                throw new Exception("Factura no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar factura: " + e.getMessage());
        }
    }

    @Transactional
    public List<Factura> listarFacturas() throws Exception {
        try {
            List<Factura> entities = this.facturaRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Factura> listarFacturasActivas() throws Exception {
        try {
            return this.facturaRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
