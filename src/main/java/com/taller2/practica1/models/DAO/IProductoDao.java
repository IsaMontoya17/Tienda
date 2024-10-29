package com.taller2.practica1.models.DAO;

import com.taller2.practica1.models.Entity.Producto;

import java.util.List;

public interface IProductoDao {
    List<Producto> findAll();
    void save(Producto producto);
    Producto findOne (Long id);
    void Delete(Long id);
}
