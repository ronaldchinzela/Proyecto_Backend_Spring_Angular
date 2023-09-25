package com.sistemasronald.springboot.backend.apirest.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sistemasronald.springboot.backend.apirest.models.entity.Cliente;
import com.sistemasronald.springboot.backend.apirest.models.services.IClienteService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200"})//OPCIONAL PARA ANGULAR. PARA INDICAR EL PUERTO QUE VA EJEUTAR EL FRONT. 
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	//Logger para 
	private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);  
	
	
	//BUSCAR Y LISTAR TODOS LOS CLIENTES
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();
	}
	
	//PAGINACIÓN PARA EL LISTADO DE CLIENTES
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 4);
		return clienteService.findAll(pageable);
	} 
	
	//BUSCAR CLIENTES POR ID
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		//Utilizamos un trycatch para capturar los errores inesperados que puedan 
		//producirse en la base de datos al momento de realizar la consulta.
		try {
			cliente = clienteService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		//Utilizamos una validación para mostrar un mensaje cuando se intenta llamar 
		//a un id de cliente que no existe en la base de datos.
		if(cliente == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	//CREAR NUEVO CLIENTE
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
		
		Cliente clienteNuevo = null;
		Map<String, Object> response = new HashMap<>();
		
		//Manejo de errores en la validación de campos
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		//Manejo de errores al registrar un cliente
		try {
			clienteNuevo = clienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", clienteNuevo);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
	}
	
	//ACTUALIZAR CLIENTE
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
		
		//Realiza una consulta del cliente que se va actualizar
		Cliente clienteActual = clienteService.findById(id);
		
		Cliente clienteUpdate = null;
		
		Map<String, Object> response = new HashMap<>();
		
		//Manejo de errores en la validación de campos
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		//Manejo de error al no encontrar el id del cliente
		if(clienteActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		//Manejo de errores al actualizar un cliente
		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			
			clienteUpdate = clienteService.save(clienteActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", clienteUpdate);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
				
	}
	
	//ELIMINAR CLIENTE
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		Cliente clienteEliminar = clienteService.findById(id);
		
		//Manejo de error al no encontrar el id del cliente
		if(clienteEliminar == null) {
			response.put("mensaje", "Error: no se pudo eliminar, el cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		//Manejo de errores al eliminar un cliente
		try {
			
			//lógica para eliminar la foto del cliente cuando este a sido eliminado.
			Cliente cliente = clienteService.findById(id);
			String nombreFotoAnterior = cliente.getFoto();
			
			if(nombreFotoAnterior != null && nombreFotoAnterior.length() > 0 ) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if(archivoFotoAnterior.exists()&&archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			
			//
			clienteService.delete(id);			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido eliminado con éxito!");
		
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	//SUBIR IMAGEN AL SERVIDOR
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
		Map<String, Object> response = new HashMap<>();
		
		Cliente cliente = clienteService.findById(id);
		
		if(!archivo.isEmpty()) {
			//crear un identificador único por cada imagen subida y concatenarlo con el nombre original de la imagen.
			String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");    
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			log.info(rutaArchivo.toString());
			
			try {
				Files.copy(archivo.getInputStream(),rutaArchivo);
			} catch (IOException e) {

				response.put("mensaje", "Error al subir la imagen del cliente" + nombreArchivo );
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//logica para borrar la imagen anterior y reemplazarla por la nueva imagen subida
			String nombreFotoAnterior = cliente.getFoto();
			
			if(nombreFotoAnterior != null && nombreFotoAnterior.length() > 0 ) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if(archivoFotoAnterior.exists()&&archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			
			//
			cliente.setFoto(nombreArchivo);
			
			clienteService.save(cliente);
			
			response.put("cliente", cliente);
			response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo); 
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	//MÉTODO PARA VER LA IMAGEN EN EL NAVEGADOR (DESCARGAR LA IMAGEN)
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		
		Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		log.info(rutaArchivo.toString());
		
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error, no se pudo cargar la imagen: " + nombreFoto);
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\" ");
		
		return  new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
		
	}
	
}











