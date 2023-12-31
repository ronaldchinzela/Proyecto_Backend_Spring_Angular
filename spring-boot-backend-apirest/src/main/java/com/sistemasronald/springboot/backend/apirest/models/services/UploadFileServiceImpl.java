package com.sistemasronald.springboot.backend.apirest.models.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService{
	
private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	
	private final static String DIRECTORIO_UPLOAD = "uploads";

	//MÉTODO PARA VALIDAR LA SUBIDA DE LA IMAGEN
	@Override
	public Resource cargar(String nombreFoto) throws MalformedURLException {
		
		Path rutaArchivo = getPath(nombreFoto);
		log.info(rutaArchivo.toString());
		
		Resource recurso = new UrlResource(rutaArchivo.toUri());
		
		if(!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
			
			recurso = new UrlResource(rutaArchivo.toUri());
			
			log.error("Error no se pudo cargar la imagen: " + nombreFoto);
			
		}
		return recurso;
	}

	//CREAR UN IDENTIFICADOR ÚNICO POR CADA IMAGEN PARA DISTINGIRLA DE LAS DEMÁS
	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		
		//crear un identificador único por cada imagen subida y concatenarlo con el nombre original de la imagen.
		String nombreArchivo = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");
		
		Path rutaArchivo = getPath(nombreArchivo);
		log.info(rutaArchivo.toString());
		
		Files.copy(archivo.getInputStream(), rutaArchivo);
		
		return nombreArchivo;
	}

	//ELIMINAR IMAGEN CUANDO SE ELIMINA EL CLIENTE
	@Override
	public boolean eliminar(String nombreFoto) {
		
		if(nombreFoto !=null && nombreFoto.length() >0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
				return true;
			}
		}
		
		return false;
	}

	//
	@Override
	public Path getPath(String nombreFoto) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}
	
}
