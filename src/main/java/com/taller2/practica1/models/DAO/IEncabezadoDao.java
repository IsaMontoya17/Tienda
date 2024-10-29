package com.taller2.practica1.models.DAO;

import com.taller2.practica1.models.Entity.Encabezado;
import com.taller2.practica1.models.Entity.Producto;

import java.util.List;

public interface IEncabezadoDao {
    Encabezado save(Encabezado encabezado);
    List<Encabezado> findByClienteId(Long clienteId);
}
