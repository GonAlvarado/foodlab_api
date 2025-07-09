package com.foodlab.api.controller;

import com.foodlab.api.model.Usuario;
import com.foodlab.api.service.PedidoService;
import com.foodlab.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, PedidoService pedidoService) {
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List listarUsuarios() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable int id) {
        return usuarioService.obtenerPorId(id);
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario nuevoUsuario) {
        return usuarioService.guardar(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizarProducto(@PathVariable int id, @RequestBody Usuario datos) {
        return usuarioService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public Boolean eliminarUsuario(@PathVariable int id) {
        return usuarioService.eliminar(id);
    }

    @GetMapping("/{id}/pedidos")
    public ResponseEntity<?> obtenerPedidos(@PathVariable Integer id) {
        List<Map<String, Object>> pedidos = pedidoService.obtenerPedidosPorUsuario(id);
        return ResponseEntity.ok(pedidos);
    }
}