package com.sistemasronald.springboot.backend.apirest.controllers;

import com.sistemasronald.springboot.backend.apirest.models.entity.Factura;
import com.sistemasronald.springboot.backend.apirest.models.entity.Producto;
import com.sistemasronald.springboot.backend.apirest.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})//OPCIONAL PARA ANGULAR. PARA INDICAR EL PUERTO QUE VA EJEUTAR EL FRONT.
@RestController
@RequestMapping("/api")
public class FacturaRestController {

    //INYECTAMOS LA CLASE SERVICE CON AUTOWIRED
    @Autowired
    private IClienteService clienteService;

    //MÉTODO PARA VER TODA LA FACTURA (DETALLE DE LA FACTURA)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})//Seguridad para que el método lo ejecute el admin y el usuario.
    @GetMapping("/facturas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Factura show(@PathVariable Long id){
        return clienteService.findFacturaById(id);
    }

    //MÉTODO PARA ELIMINAR FACTURA
    @Secured({"ROLE_ADMIN"})//Seguridad para que el método lo ejecute el admin.
    @DeleteMapping("/facturas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        clienteService.deleteFacturaById(id);
    }

    //MÉTODO PARA BUSCAR PRODUCTOS POR CARACTERES
    @Secured({"ROLE_ADMIN"})//Seguridad para que el método lo ejecute el admin.
    @GetMapping("/facturas/filtrar-productos/{termino}")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> filtrarProductos(@PathVariable String termino){
        return clienteService.findByNombreContainingIgnoreCase(termino);
    }

    //MÉTODO PARA CREAR LA FACTURA
    @Secured({"ROLE_ADMIN"})//Seguridad para que el método lo ejecute el admin.
    @PostMapping("/facturas")
    @ResponseStatus(HttpStatus.CREATED)
    public Factura crear(@RequestBody Factura factura){
        return clienteService.saveFactura(factura);
    }

}
