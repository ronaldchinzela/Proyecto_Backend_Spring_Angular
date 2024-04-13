package com.sistemasronald.springboot.backend.apirest.models.services;

import java.util.List;

import com.sistemasronald.springboot.backend.apirest.models.dao.IFacturaDao;
import com.sistemasronald.springboot.backend.apirest.models.dao.IProductoDao;
import com.sistemasronald.springboot.backend.apirest.models.entity.Factura;
import com.sistemasronald.springboot.backend.apirest.models.entity.Producto;
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

	//IMPORTAMOS LOS DAO PARA PODER IMPLEMENTAR LOS MÉTODOS PARA LOS CRUDs
	@Autowired
	private IClienteDao clienteDao;
	@Autowired
	private IFacturaDao facturaDao;
	@Autowired
	private IProductoDao productoDao;
	
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

	//MÉTODO BUSCAR FACTURA POR ID
	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null); //Si no encuentra la factura, retorna un null.
	}

	//MÉTODO CREAR FACTURA
	@Override
	@Transactional
	public Factura saveFactura(Factura factura) {
		return facturaDao.save(factura);
	}

	//MÉTODO ELIMINAR FACTURA
	@Override
	@Transactional
	public void deleteFacturaById(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional
	public List<Producto> findByNombreContainingIgnoreCase(String termino) {

		return productoDao.findByNombreContainingIgnoreCase(termino);
	}

}
