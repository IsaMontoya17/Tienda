package com.taller2.practica1.models.DAO.Impl;

import com.taller2.practica1.models.DAO.IEncabezadoDao;
import com.taller2.practica1.models.Entity.Encabezado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EncabezadoDaoImp implements IEncabezadoDao {

    private static final Logger logger = Logger.getLogger(EncabezadoDaoImp.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Encabezado save(Encabezado encabezado) {
        try {
            if (encabezado.getId() != null && encabezado.getId() > 0) {
                Encabezado existingEncabezado = em.find(Encabezado.class, encabezado.getId());
                if (existingEncabezado != null) {
                    encabezado = em.merge(encabezado);
                    logger.info("Encabezado actualizado con éxito: " + encabezado);
                } else {
                    logger.warn("Encabezado con ID " + encabezado.getId() + " no encontrado para actualizar.");
                }
            } else {
                em.persist(encabezado);
                logger.info("Encabezado guardado con éxito: " + encabezado);
            }
        } catch (Exception e) {
            logger.error("Error al guardar/actualizar el encabezado: " + encabezado, e);
        }
        return encabezado;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Encabezado> findByClienteId(Long clienteId) {
        logger.info("Buscando encabezados para el cliente con ID: " + clienteId);
        List<Encabezado> encabezados = em.createQuery("SELECT e FROM Encabezado e WHERE e.cliente.id = :clienteId", Encabezado.class)
                .setParameter("clienteId", clienteId)
                .getResultList();
        logger.info("Número de encabezados encontrados para el cliente: " + encabezados.size());
        return encabezados;
    }

}
