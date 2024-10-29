package com.taller2.practica1.models.DAO;

import com.taller2.practica1.models.Entity.Cliente;

import java.util.List;

public interface IClienteDao {
    List<Cliente> findAll();
    void save(Cliente cliente);
    Cliente findOne (Long id);
    void Delete(Long id);
}
