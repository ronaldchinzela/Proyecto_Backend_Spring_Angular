package com.sistemasronald.springboot.backend.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*Clase Factura que tiene una relación con la clase Cliente (Muchas facturas tienen relación con un cliente)*/
@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String observacion;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)//Le damos un formato de fecha, en este caso un formato completo.
    private Date createAt;

    @JsonIgnoreProperties(value = {"facturas", "hibernateLazyInitializer", "handler"}, allowSetters = true) //Ignoramos la relación inversa (en este caso, ingnoramos facturas) y también ignoramos otros atributos basura ("hibernateLazyInitializer" y "handler").
    @ManyToOne(fetch = FetchType.LAZY)//Indicamos que muchas facturas están asociadas a un solo cliente. Many pertenece a la clase "Factura" y one a la clase "Cliente".
    private Cliente cliente;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //Ignoramos los atributos basura "hibernateLazyInitializer" y "handler".
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //Muchos items en una factura.
    @JoinColumn(name = "factura_id")//Asignamos un nombre a la llave foranea
    private List<ItemFactura> items;

    //CREAMOS UN CONSTRUCTOR E INICIALIZAMOS items COMO UN ARRAY LIST
    public Factura() {
        items = new ArrayList<>();
    }

    //MÉTODO PARA ASIGNAR (OBTENER LA FECHA) ANTES DE GUARDAR LA FACTURA.
    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }

    //MÉTODO PARA CALCULAR EL TOTAL DE TODOS LOS ITEMS (Recorremos todos los items de la factura y los vamos sumando)
    public Double getTotal(){
        Double total = 0.00;
        for(ItemFactura item: items){
            total += item.getImporte();
        }
        return total;
    }

    private static final long serialVersionUID = 1L;
}
