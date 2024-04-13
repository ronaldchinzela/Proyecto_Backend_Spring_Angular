package com.sistemasronald.springboot.backend.apirest.models.services;

import java.util.List;

import com.sistemasronald.springboot.backend.apirest.models.entity.Factura;
import com.sistemasronald.springboot.backend.apirest.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistemasronald.springboot.backend.apirest.models.entity.Cliente;
import com.sistemasronald.springboot.backend.apirest.models.entity.Region;

public interface IClienteService {

	//MÉTODOS DEL CRUD PARA EL CLIENTE
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);  
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public	void delete(Long id);
	
	public List<Region> findAllRegiones();

	//MÉTODOS DEL CRUD PARA LA FACTURA

	//Buscar factura por Id
	public Factura findFacturaById(Long id);

	//Crear factura
	public Factura saveFactura(Factura factura);

	//Eliminar factura
	public void deleteFacturaById(Long id);

	//Busca producto por caracteres que se ingresan como parámetro
	public List<Producto> findByNombreContainingIgnoreCase(String termino);
}
