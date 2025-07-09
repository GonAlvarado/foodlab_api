package com.foodlab.api.controller;

import com.foodlab.api.model.Producto;
import com.foodlab.api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List listarProductos() {
        return productoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable int id) {
        return productoService.obtenerPorId(id);
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto nuevoProducto) {
        return productoService.guardar(nuevoProducto);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable int id, @RequestBody Producto datos) {
        return productoService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public Boolean eliminarProducto(@PathVariable int id) {
        return productoService.eliminar(id);
    }
}
