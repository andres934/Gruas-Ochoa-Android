package com.example.andre_000.mrservice;

/**
 * Created by andre_000 on 11/23/2015.
 */
public class Empresa extends JsonEntity {

    public Integer id; // El json  se lo trae como "id", no "empresa_id" por eso no lo llenaba
    public String nombre_empresa;
    public String rif_empresa;
    public String direccion_empresa;
    public String telefono_empresa;

    @Override
    public String toString() {
        return nombre_empresa;
    }
}
