package com.example.andre_000.mrservice;

/**
 * Created by andre_000 on 11/24/2015.
 */
public class Servicios extends JsonEntity {

    public Integer id;
    public String origen;
    public String reforigen;
    public String destino;
    public String refdestino;
    public String tipo_servicio;
    public String tipo_cliente;
    public String monto;
    public String placa;
    public String kilometros;
    public Integer servicios_pagados;
    public Integer chofer_id;
    public Integer users_id;
    //public Integer empresas_id; No hay un campo en json que se llame asi
    public String created_at;

    @Override
    public String toString() {
        return id+origen+reforigen+destino+refdestino+tipo_servicio+tipo_cliente+monto+chofer_id+users_id+placa+kilometros+servicios_pagados+created_at;
    }
}
