package com.foodlab.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    private String usuario;
    protected String contrasenia;

    public Integer getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        if (usuario != null && !usuario.trim().isEmpty()){
            this.usuario = usuario;
        }
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        if (contrasenia != null && !contrasenia.trim().isEmpty()){
            this.contrasenia = contrasenia;
        }
    }
}