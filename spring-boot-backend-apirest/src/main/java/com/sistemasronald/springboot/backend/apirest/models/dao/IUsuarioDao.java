package com.sistemasronald.springboot.backend.apirest.models.dao;

import com.sistemasronald.springboot.backend.apirest.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    //MÉTODO PARA RETORNAR AL USUARIO
    public Usuario findByUsername(String username);


}
