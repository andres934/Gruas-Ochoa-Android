package com.example.andre_000.mrservice;

/**
 * Created by andre_000 on 11/24/2015.
 */
public class Choferes extends JsonEntity {

    public Integer id;
    public String nombre_chofer;
    public String cedula_chofer;
    public String estado;
    public String telefono_chofer;
    public Integer servicios_realizados;
    public Integer users_id;
    public Integer gruas_id;

    @Override
    public String toString() {
        return nombre_chofer;
    }
}
