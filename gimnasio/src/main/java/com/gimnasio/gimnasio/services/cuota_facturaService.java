package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.cuota_factura;
import com.gimnasio.gimnasio.repositories.cuota_facturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cuota_facturaService {

    @Autowired
    private cuota_facturaRepository cuotaFacturaRepository;

    public void crearCuotaFactura(String idCuota, String idFactura) throws Exception {
        try {
            cuota_factura cuotaFactura = new cuota_factura();
            cuotaFactura.setId_cuota(idCuota);
            cuotaFactura.setId_factura(idFactura);
            cuotaFacturaRepository.save(cuotaFactura);
        } catch (Exception e) {
            throw new Exception("Error al crear la relación cuota-factura: " + e.getMessage());
        }
    }

}
