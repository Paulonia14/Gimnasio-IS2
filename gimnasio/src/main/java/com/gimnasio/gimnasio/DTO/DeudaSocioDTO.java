package com.gimnasio.gimnasio.DTO;

import com.gimnasio.gimnasio.entities.Socio;

public class DeudaSocioDTO {

    private String socioId;
    private Long numeroSocio;
    private String nombreCompleto;
    private int mesesDeuda;
    private double totalDeuda;

    public DeudaSocioDTO(Socio socio, int mesesDeuda, double totalDeuda) {
        this.socioId = socio.getId();
        this.numeroSocio = socio.getNumeroSocio();
        this.nombreCompleto = socio.getApellido() + ", " + socio.getNombre();
        this.mesesDeuda = mesesDeuda;
        this.totalDeuda = totalDeuda;
    }

    public String getSocioId() {
        return socioId;
    }

    public Long getNumeroSocio() {
        return numeroSocio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getMesesDeuda() {
        return mesesDeuda;
    }

    public double getTotalDeuda() {
        return totalDeuda;
    }
}
