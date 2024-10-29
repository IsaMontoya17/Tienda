package com.taller2.practica1.models.DAO.Impl;

import com.taller2.practica1.models.DAO.IDetalleDao;
import com.taller2.practica1.models.Entity.Detalle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DetallesDaoImp implements IDetalleDao {

    private static final Logger logger = Logger.getLogger(DetallesDaoImp.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Detalle save(Detalle detalle) {
        try {
            if (detalle.getId() != null && detalle.getId() > 0) {
                Detalle existingDetalle = em.find(Detalle.class, detalle.getId());
                if (existingDetalle != null) {
                    detalle = em.merge(detalle);
                    logger.info("Detalle actualizado con éxito: " + detalle);
                } else {
                    logger.warn("Detalle con ID " + detalle.getId() + " no encontrado para actualizar.");
                }
            } else {
                em.persist(detalle);
                logger.info("Detalle guardado con éxito: " + detalle);
            }
        } catch (Exception e) {
            logger.error("Error al guardar/actualizar el detalle: " + detalle, e);
        }
        return detalle;
    }

}
