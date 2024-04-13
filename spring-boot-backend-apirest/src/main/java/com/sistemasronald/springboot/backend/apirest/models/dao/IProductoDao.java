package com.sistemasronald.springboot.backend.apirest.models.dao;

import com.sistemasronald.springboot.backend.apirest.models.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductoDao extends CrudRepository<Producto, Long> {

    //Utilizamos Containing y IgnoreCase en el nombre del método findByNombre para buscar
    //en cualquier parte de la cadena del nombre del producto, los caracteres que se ingresen como parámetro.
    public List<Producto> findByNombreContainingIgnoreCase(String termino);

    //Utilizamos StartingWith y IgnoreCase en el nombre del método findByNombre para buscar el caracter
    //al comienzo (o que comience) de la cadena del nombre del producto, los caracteres que se ingresen como parámetro.
    /*public List<Producto> findByNombreStartingWithIgnoreCase(String termino);*/

}
