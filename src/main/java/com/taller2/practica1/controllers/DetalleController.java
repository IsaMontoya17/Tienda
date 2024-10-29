package com.taller2.practica1.controllers;

import com.taller2.practica1.models.DAO.IDetalleDao;
import com.taller2.practica1.models.DAO.IProductoDao;
import com.taller2.practica1.models.Entity.Detalle;
import com.taller2.practica1.models.Entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/detalles")
public class DetalleController {

    @Autowired
    private IDetalleDao detalleDao;

    @Autowired
    private IProductoDao productoDao;

    @PostMapping("/guardarDetalles")
    public ResponseEntity<?> guardarDetalles(@RequestBody List<Detalle> detalles) {
        for (Detalle detalle : detalles) {
            Producto producto = productoDao.findOne(detalle.getProducto().getId());
            if (producto == null) {
                return ResponseEntity.badRequest().body("Producto no encontrado con ID " + detalle.getProducto().getId());
            }
            int nuevoStock = producto.getStock() - detalle.getCantidad();
            if (nuevoStock < 0) {
                return ResponseEntity.badRequest().body("No hay suficiente stock para el producto " + producto.getNombre());
            }
            producto.setStock(nuevoStock);
            productoDao.save(producto);
        }
        for (Detalle detalle : detalles) {
            detalleDao.save(detalle);
        }

        return ResponseEntity.ok().build();
    }

}
