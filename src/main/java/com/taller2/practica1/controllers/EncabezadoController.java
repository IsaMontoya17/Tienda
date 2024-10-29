package com.taller2.practica1.controllers;

import com.taller2.practica1.models.DAO.IDetalleDao;
import com.taller2.practica1.models.DAO.IEncabezadoDao;
import com.taller2.practica1.models.DAO.IProductoDao;
import com.taller2.practica1.models.Entity.Detalle;
import com.taller2.practica1.models.Entity.Encabezado;
import com.taller2.practica1.models.Entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Controller
@RequestMapping("/encabezado")
public class EncabezadoController {

    @Autowired
    private IEncabezadoDao encabezadoDao;

    @Autowired
    private IDetalleDao detalleDao;

    @Autowired
    private IProductoDao productoDao;

    @PostMapping("/realizarCompra")
    @ResponseBody
    public ResponseEntity<Long> guardarEncabezado(@RequestBody Encabezado encabezado) {
        try {
            Encabezado nuevoEncabezado = encabezadoDao.save(encabezado);

            List<Detalle> detalles = encabezado.getDetalles();
            for (Detalle detalle : detalles) {
                Producto producto = productoDao.findOne(detalle.getProducto().getId());

                if (producto == null || producto.getStock() < detalle.getCantidad()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(null);
                }

                producto.setStock(producto.getStock() - detalle.getCantidad());
                productoDao.save(producto);
            }

            return ResponseEntity.ok(nuevoEncabezado.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
