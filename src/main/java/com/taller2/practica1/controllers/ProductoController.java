package com.taller2.practica1.controllers;

import com.taller2.practica1.models.DAO.IProductoDao;
import com.taller2.practica1.models.Entity.Producto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private IProductoDao productoDao;

    @GetMapping("/listarProductos")
    public String Listar(Model model) {
        model.addAttribute("titulo", "Listado de Productos");
        model.addAttribute("productos", productoDao.findAll());
        return "listarProductos";
    }

    @GetMapping("/formProductos")
    public String crear(Model model) {
        Producto producto = new Producto();
        model.addAttribute("titulo", "Formulario de Producto");
        model.addAttribute("producto", producto);
        return "formProductos";
    }

    @PostMapping("/formProductos")
    public String Guardar(@Valid Producto producto, BindingResult result, Model model) {
        System.out.println("Producto recibido: " + producto);

        if (result.hasErrors()) {
            System.out.println("Errores de validación: " + result.getAllErrors());
            model.addAttribute("titulo", "Formulario de Producto");
            return "formProductos";
        }

        productoDao.save(producto);
        return "redirect:/productos/listarProductos";
    }


    @GetMapping("/formProductos/{id}")
    public String Editar(@PathVariable Long id, Model model) {
        Producto producto = null;

        if (id > 0) {
            producto = productoDao.findOne(id);
        } else {
            return "redirect:/productos/listarProductos";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        return "formProductos";
    }

    @GetMapping("/eliminar/{id}")
    public String Eliminar(@PathVariable Long id) {
        if (id > 0) {
            productoDao.Delete(id);
        }
        return "redirect:/productos/listarProductos";
    }
}