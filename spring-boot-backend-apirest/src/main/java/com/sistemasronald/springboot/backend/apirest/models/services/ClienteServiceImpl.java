package com.sistemasronald.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemasronald.springboot.backend.apirest.models.dao.IClienteDao;
import com.sistemasronald.springboot.backend.apirest.models.entity.Cliente;
import com.sistemasronald.springboot.backend.apirest.models.entity.Region;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	//MÉTODO LISTAR CLIENTES
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() { 
		return (List<Cliente>) clienteDao.findAll();
	}
	
	//MÉTODO PARA LA PAGINACIÓN
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);  
	}

	//MÉTODO BUSCAR CLIENTE POR ID
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	//MÉTODO GUARDAR CLIENTE
	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	//MÉTODO ELIMINAR CLIENTE POR ID
	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
		
	}
	
	//MÉTODO LISTAR REGIONES
	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		return clienteDao.findAllRegiones();
	}

}
