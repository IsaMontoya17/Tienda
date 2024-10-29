package com.taller2.practica1.models.DAO.Impl;

import com.taller2.practica1.models.DAO.IProductoDao;
import com.taller2.practica1.models.Entity.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductoDaoImp implements IProductoDao {

    private static final Logger logger = Logger.getLogger(ProductoDaoImp.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Producto> findAll() {
        logger.info("Buscando todos los productos.");
        List<Producto> productos = em.createQuery("from Producto").getResultList();
        logger.info("Número de productos encontrados: " + productos.size());
        return productos;
    }

    @Transactional
    @Override
    public void save(Producto producto) {
        if (producto.getId() != null && producto.getId() > 0) {
            try {
                Producto existingProducto = em.find(Producto.class, producto.getId());
                if (existingProducto != null) {
                    em.merge(producto);
                    logger.info("Producto actualizado con éxito: " + producto);
                } else {
                    logger.warn("Producto con ID " + producto.getId() + " no encontrado para actualizar.");
                }
            } catch (Exception e) {
                logger.error("Error al actualizar el producto: " + producto, e);
            }
        } else {
            try {
                em.persist(producto);
                logger.info("Producto guardado con éxito: " + producto);
            } catch (Exception e) {
                logger.error("Error al guardar el producto: " + producto, e);
            }
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Producto findOne(Long id) {
        logger.info("Buscando producto con ID: " + id);
        Producto producto = null;
        try {
            producto = em.find(Producto.class, id);
            if (producto != null) {
                logger.info("Producto encontrado: " + producto);
            } else {
                logger.info("Producto con ID " + id + " no encontrado.");
            }
        } catch (NoResultException e) {
            logger.error("No se encontró el producto con ID: " + id, e);
        } catch (Exception e) {
            logger.error("Error al buscar el producto con ID: " + id, e);
        }
        return producto;
    }

    @Transactional
    @Override
    public void Delete(Long id) {
        logger.info("Eliminando producto con ID: " + id);
        try {
            Producto producto = findOne(id);
            if (producto != null) {
                em.remove(producto);
                logger.info("Producto eliminado con éxito: " + producto);
            } else {
                logger.warn("Producto con ID " + id + " no encontrado, no se realizó eliminación.");
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el producto con ID: " + id, e);
        }
    }
}