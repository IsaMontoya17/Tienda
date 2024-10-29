package com.taller2.practica1.models.DAO.Impl;

import com.taller2.practica1.models.DAO.IClienteDao;
import com.taller2.practica1.models.Entity.Cliente;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;

import java.util.List;

@Repository
public class ClienteDaoImp implements IClienteDao {

    private static final Logger logger = Logger.getLogger(ClienteDaoImp.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        logger.info("Buscando todos los clientes.");
        List<Cliente> clientes = em.createQuery("from Cliente").getResultList();
        logger.info("Número de clientes encontrados: " + clientes.size());
        return clientes;
    }

    @Transactional
    @Override
    public void save(Cliente cliente) {
        if (cliente.getId() != null && cliente.getId() > 0) {
            try {
                Cliente existingCliente = em.find(Cliente.class, cliente.getId());
                if (existingCliente != null) {
                    cliente.setCreateAt(existingCliente.getCreateAt());
                    em.merge(cliente);
                    logger.info("Cliente actualizado con éxito:" + cliente);
                } else {
                    logger.warn("Cliente con ID " + cliente.getId() + " no encontrado para actualizar.");
                }
            } catch (Exception e) {
                logger.error("Error al actualizar el cliente: " + cliente, e);
            }
        } else {
            try {
                em.persist(cliente);
                logger.info("Cliente guardado con éxito: " + cliente);
            } catch (Exception e) {
                logger.error("Error al guardar el cliente: " + cliente, e);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findOne(Long id) {
        logger.info("Buscando cliente con ID: " + id);
        Cliente cliente = null;
        try {
            cliente = em.find(Cliente.class, id);
            if (cliente != null) {
                logger.info("Cliente encontrado: " + cliente);
            } else {
                logger.info("Cliente con ID " + id + " no encontrado.");
            }
        } catch (NoResultException e) {
            logger.error("No se encontró el cliente con ID: " + id, e);
        } catch (Exception e) {
            logger.error("Error al buscar el cliente con ID: " + id, e);
        }
        return cliente;
    }

    @Transactional
    @Override
    public void Delete(Long id) {
        logger.info("Eliminando cliente con ID: " + id);
        try {
            Cliente cliente = findOne(id);
            if (cliente != null) {
                em.remove(cliente);
                logger.info("Cliente eliminado con éxito: " + cliente);
            } else {
                logger.warn("Cliente con ID " + id + " no encontrado, no se realizó eliminación.");
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el cliente con ID: " + id, e);
        }
    }
}
