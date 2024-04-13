package com.sistemasronald.springboot.backend.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    //CREANDO RELACION ENTRE item Y producto (MUCHOS item CONTIENEN UN PRODUCTO).
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //Ignoramos los atributos basura "hibernateLazyInitializer" y "handler".
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    //MÉTODO PARA CALCULAR EL IMPORTE TOTAL A PAGAR
    public Double getImporte(){
        return cantidad.doubleValue()*producto.getPrecio();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    private static final long serialVersionUID = 1L;


}
