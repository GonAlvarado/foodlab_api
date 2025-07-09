package com.foodlab.api.service;

import com.foodlab.api.model.Usuario;
import com.foodlab.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario obtenerPorId(int id) {
        return repository.findById(id).orElse(null);
    }

    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario actualizar(int id, Usuario datos) {
        Usuario usuario = obtenerPorId(id);
        if (usuario != null) {
            usuario.setUsuario(datos.getUsuario());
            usuario.setContrasenia(datos.getContrasenia());
            return repository.save(usuario);
        }
        return null;
    }

    public boolean eliminar(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
