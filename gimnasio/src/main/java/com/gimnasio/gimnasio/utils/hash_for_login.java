package com.gimnasio.gimnasio.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//tecnica para encriptar la clave del usuario
public class hash_for_login {
    public String hashClave(String clave) throws NoSuchAlgorithmException {
        try {
            //Se utiliza el algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Calcula el hash y devuelve los bytes
            byte[] hashedBytes = md.digest(clave.getBytes());

            // Convierte los bytes en String
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                //Convierte cada byte a hexadecimal y lo añade al StringBuilder
                sb.append(String.format("%02x", b));
            }
            //Se devuelve la clave con el hash como String
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error encriptando la clave", ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error de sistema", ex);
        }
    }
}